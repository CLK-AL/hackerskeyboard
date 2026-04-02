plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

val langchain4jVersion = "0.35.0"
val camelVersion = "4.4.0"
val djlVersion = "0.27.0"
val jlamaVersion = "0.4.0"
val kotlinxSerializationVersion = "1.6.3"
val ktorVersion = "2.3.9"
val coroutinesVersion = "1.8.0"

dependencies {
    // Kotlin
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")

    // HTTP Client for LM Studio and REST APIs
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    // LangChain4j - LLM orchestration framework
    implementation("dev.langchain4j:langchain4j:$langchain4jVersion")
    implementation("dev.langchain4j:langchain4j-core:$langchain4jVersion")
    implementation("dev.langchain4j:langchain4j-ollama:$langchain4jVersion")
    implementation("dev.langchain4j:langchain4j-local-ai:$langchain4jVersion")
    implementation("dev.langchain4j:langchain4j-open-ai:$langchain4jVersion")

    // Apache Camel AI components
    implementation("org.apache.camel:camel-core:$camelVersion")
    implementation("org.apache.camel:camel-langchain4j:$camelVersion")
    implementation("org.apache.camel:camel-http:$camelVersion")

    // JLama - Pure Java GGUF inference
    implementation("com.github.tjake:jlama-core:$jlamaVersion")
    implementation("com.github.tjake:jlama-native:$jlamaVersion")

    // DJL - Deep Java Library for model inference
    implementation("ai.djl:api:$djlVersion")
    implementation("ai.djl.huggingface:tokenizers:$djlVersion")
    implementation("ai.djl.pytorch:pytorch-engine:$djlVersion")
    implementation("ai.djl.pytorch:pytorch-native-auto:2.1.1")

    // SLF4J logging
    implementation("org.slf4j:slf4j-api:2.0.12")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.3")

    // Testing
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    testImplementation("io.ktor:ktor-client-mock:$ktorVersion")
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}
