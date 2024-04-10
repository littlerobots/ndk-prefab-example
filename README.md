# Example Android NDK prefab project

This example shows an approach to use prefab packages in your project for native dependencies between
modules in the same project. In particular this setup shows how to create a module for the popular
[llama.cpp](https://github.com/ggerganov/llama.cpp) and how to use llama.cpp with [java-llama.cpp](https://github.com/kherud/java-llama.cpp).

This is the same approach as I demonstrated in my talk [LLMs on small devices](https://speakerdeck.com/hugovisser/llms-on-small-devices-dutchaug).

## General structure
Each native module is called `<library>-android` and within that directory the library sources are
included as a git submodule or git subtree.
The nested structure allows for either a custom `CMakeLists.txt` file that can include or override
the library `CMakeLists.txt` file. What is best or convenient will depend on the specific library used.

In the example, the `CMakeLists.txt` for `llama.cpp` just includes the `llama.cpp` `CMakeLists.txt` file,
while the original `CMakeLists.txt` file for `java-llama.cpp` is not used at all.

## Dependencies
Because `llama.cpp` is a [prefab](https://developer.android.com/build/native-dependencies) `aar`, 
the `java-llama.cpp` can depend on this package through Gradle. The `CMakeLists.txt` file then
adds the `llamacpp-android::common` and `llamacpp-android::llama` as libraries for the cmake project.

