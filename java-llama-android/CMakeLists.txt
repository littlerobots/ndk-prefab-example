project(jllama-android CXX)
cmake_minimum_required(VERSION 3.22)

# Make sure we have the llama.cpp android dependency available
find_package("llamacpp-android" CONFIG REQUIRED)

option(LLAMA_VERBOSE "llama: verbose output" OFF)

# Fetch another dependency with cmake
include(FetchContent)
FetchContent_Declare(
        json
        GIT_REPOSITORY https://github.com/nlohmann/json
        GIT_TAG v3.11.3
)
FetchContent_MakeAvailable(json)

add_library(jllama SHARED java-llama.cpp/src/main/cpp/jllama.cpp java-llama.cpp/src/main/cpp/server.hpp java-llama.cpp/src/main/cpp/utils.hpp)
target_include_directories(jllama PRIVATE java-llama.cpp/src/main/cpp ${JNI_INCLUDE_DIRS})
target_link_libraries(jllama PRIVATE llamacpp-android::common llamacpp-android::llama nlohmann_json ${LLAMA_EXTRA_LIBS})
target_compile_features(jllama PRIVATE cxx_std_11)

target_compile_definitions(jllama PRIVATE
        SERVER_VERBOSE=$<BOOL:${LLAMA_VERBOSE}>
)
