#!/usr/bin/env python3
"""
emoji_loader.py - טוען אמוג'י עם תרגום עברי
גרסה 3 - תרגום ברמת ביטויים עם סדר מילים עברי
"""
import re
from pathlib import Path
from typing import Dict, List, Tuple
from dataclasses import dataclass

# תו הגרש המתולתל
APOS = "\u2019"  # '


class EmojiDictionary:
    """מילון תרגום עם תמיכה בביטויים"""
    
    def __init__(self, dict_path: str = "emoji-dictionary.txt"):
        self.groups: Dict[str, str] = {}
        self.subgroups: Dict[str, str] = {}
        self.words: Dict[str, str] = {}
        self.countries: Dict[str, str] = {}
        self.stop_words: set = set()
        self.phrases: Dict[str, str] = {}
        
        self._load(dict_path)
        self._init_phrases()
    
    def _load(self, path: str):
        current_section = None
        
        with open(path, 'r', encoding='utf-8') as f:
            for line in f:
                line = line.strip()
                
                if not line or line.startswith('#'):
                    continue
                
                if line.startswith('[') and line.endswith(']'):
                    current_section = line[1:-1]
                    continue
                
                if '=' in line and current_section:
                    key, value = line.split('=', 1)
                    key = key.strip()
                    value = value.strip()
                    
                    if current_section == 'groups':
                        self.groups[key] = value
                    elif current_section == 'subgroups':
                        self.subgroups[key] = value
                    elif current_section == 'words':
                        self.words[key.lower()] = value
                    elif current_section == 'countries':
                        self.countries[key.lower()] = value
                    elif current_section == 'phrases':
                        # ביטויים נשמרים עם המפתח המקורי (case-sensitive)
                        self.phrases[key] = value
                
                elif current_section == 'stop_words':
                    self.stop_words.add(line.lower())
    
    def _init_phrases(self):
        """הביטויים נטענים מהמילון בסעיף [phrases]"""
        pass
    

    def translate_name(self, name: str, is_flag: bool = False) -> str:
        # דגל מדינה
        if is_flag and 'flag:' in name:
            country = name.split('flag:')[1].strip()
            for key, he_name in self.countries.items():
                if key.lower() == country.lower():
                    return f'דגל {he_name}'
            return f'דגל {country}'
        
        name_lower = name.lower()
        
        # תרגום מדויק
        for pattern, translation in self.phrases.items():
            if pattern.lower() == name_lower:
                return translation
        
        # הסרת skin tone - חייב להיות בסדר הזה (הארוכים קודם!)
        base_name = name
        skin_suffix = ''
        
        skin_tones = [
            ('medium-light skin tone', 'בהיר בינוני'),
            ('medium-dark skin tone', 'כהה בינוני'),
            ('medium skin tone', 'בינוני'),
            ('light skin tone', 'בהיר'),
            ('dark skin tone', 'כהה'),
        ]
        
        for skin, he_skin in skin_tones:
            if skin in name_lower:
                # הסר את ה-skin tone מהשם
                base_name = name_lower.replace(': ' + skin, '').replace(', ' + skin, '').replace(skin, '').strip()
                skin_suffix = f' {he_skin}'
                break
        
        # תרגום בסיס - נסה למצוא ביטוי מתאים
        for pattern, translation in self.phrases.items():
            if pattern.lower() == base_name.lower():
                return translation + skin_suffix
        
        # אם יש skin tone והבסיס לא נמצא - נסה לתרגם את הבסיס
        if skin_suffix:
            base_translation = self._translate_words(base_name)
            return base_translation + skin_suffix
        
        # גיבוי - תרגום מילים
        return self._translate_words(name)
    
    def _translate_words(self, name: str) -> str:
        words = re.split(r'[\s\-:,]+', name)
        translated = []
        
        for word in words:
            word_clean = word.strip().lower()
            if not word_clean or word_clean in self.stop_words:
                continue
            
            if word_clean in self.words:
                translated.append(self.words[word_clean])
            elif word_clean in self.countries:
                translated.append(self.countries[word_clean])
            else:
                translated.append(word)
        
        return ' '.join(translated) if translated else name

    def translate_group(self, group: str) -> str:
        return self.groups.get(group, group)
    
    def translate_subgroup(self, subgroup: str) -> str:
        return self.subgroups.get(subgroup, subgroup)



@dataclass
class Emoji:
    char: str
    version: str
    name_en: str
    name_he: str
    group_he: str
    subgroup_he: str


class EmojiLoader:
    def __init__(self, dictionary: EmojiDictionary):
        self.dict = dictionary
        self.emojis: List[Emoji] = []
    
    def load(self, emoji_test_path: str) -> List[Emoji]:
        self.emojis = []
        current_group = ""
        current_subgroup = ""
        
        with open(emoji_test_path, 'r', encoding='utf-8') as f:
            for line in f:
                line = line.strip()
                
                if line.startswith('# group:'):
                    current_group = line.replace('# group:', '').strip()
                elif line.startswith('# subgroup:'):
                    current_subgroup = line.replace('# subgroup:', '').strip()
                elif line and not line.startswith('#') and 'fully-qualified' in line:
                    emoji = self._parse_line(line, current_group, current_subgroup)
                    if emoji:
                        self.emojis.append(emoji)
        
        return self.emojis
    
    def _parse_line(self, line: str, group: str, subgroup: str) -> Emoji | None:
        try:
            # פורמט: 1F600 ; fully-qualified # 😀 E1.0 grinning face
            parts = line.split('#')
            if len(parts) < 2:
                return None
            
            # חלק אחרי # מכיל: 😀 E1.0 grinning face
            after_hash = parts[1].strip()
            match = re.match(r'(\S+)\s+(E[\d.]+)\s+(.+)', after_hash)
            if not match:
                return None
            
            char = match.group(1)
            version = match.group(2)
            name_en = match.group(3).strip()
            is_flag = (group == 'Flags')
            
            return Emoji(
                char=char,
                version=version,
                name_en=name_en,
                name_he=self.dict.translate_name(name_en, is_flag),
                group_he=self.dict.translate_group(group),
                subgroup_he=self.dict.translate_subgroup(subgroup)
            )
        except Exception as e:
            return None
    
    def search(self, query: str) -> List[Emoji]:
        q = query.lower()
        return [e for e in self.emojis if q in e.name_he.lower() or q in e.name_en.lower()]
    
    def export_txt(self, output_path: str):
        with open(output_path, 'w', encoding='utf-8') as f:
            f.write("# emoji.txt - מאגר אמוג'י עברי\n")
            f.write("# Unicode 17.0\n")
            f.write("# פורמט: EMOJI תיאור ; English\n\n")
            
            current_group = ""
            current_subgroup = ""
            
            for emoji in self.emojis:
                if emoji.group_he != current_group:
                    current_group = emoji.group_he
                    f.write(f"\n# ====== {emoji.group_he} ======\n")
                
                if emoji.subgroup_he != current_subgroup:
                    current_subgroup = emoji.subgroup_he
                    f.write(f"# --- {emoji.subgroup_he} ---\n")
                
                f.write(f"{emoji.char} {emoji.name_he} ; {emoji.name_en}\n")
            
            f.write(f"\n# סה\"כ: {len(self.emojis)} אמוג'ים\n")


if __name__ == "__main__":
    dictionary = EmojiDictionary("emoji-dictionary-sorted.txt")
    print(f"נטענו: {len(dictionary.words)} מילים, {len(dictionary.phrases)} ביטויים")
    
    loader = EmojiLoader(dictionary)
    emojis = loader.load("emoji-test.txt")
    print(f"נטענו: {len(emojis)} אמוג'ים")
    
    loader.export_txt("emoji-v3.txt")
    
    print("\n--- דוגמאות ---")
    for emoji in emojis[:20]:
        print(f"{emoji.char} {emoji.name_he}")
