# Better-CC lib

**Better-CC lib** - C library based of [**Coverett**](https://github.com/Bs0Dd/Coverett) for the [**Open Computers II**](https://github.com/fnuecke/oc2) mod.

## Warning!!!

The library is in alpha stage, so it can contain a bunch of nasty bugs, glitches and other things.  
Documentation available [here](https://github.com/Bs0Dd/Coverett/wiki).

## Complectation

* `libbetterccso` - The library itself in a dynamic version. Required for the utilities supplied with it.

* `fimexu` - File IMport/EXport Utility. Exchanges files between the mod's computer and a real PC (faster than standard `import.lua` and `export.lua`). 

* `lshldev` - Just lists all HLAPI devices on the system. `lsdev.lua` analog.

* `redstone` - Small program for Redstone Interface Block or Redstone Interface Card control.

* `seplay` - Small Sound effects player for Sound Card. Can search effects names by query.

## Building note

Firstly you need to download a **MUSL** compiler for **RISC-V 64** architecture. You can download it in [this site](https://musl.cc/) or directly by [this link (Linux)](https://musl.cc/riscv64-linux-musl-cross.tgz). Unfortunately Windows version is not available now.  
Now you need to have **git** on your machine. Write these commands to clone repo:
```bash
git clone https://github.com/ajh123-development/better-cc/
git submodule init
git submodule update --recursive
cd src/main/programs-source
```

For building you need to have **CMake**. Type this to generate a makefile:
```bash
mkdir build
cd build
CC='<folder with downloaded compiler>/bin/riscv64-linux-musl-gcc' cmake CMakeLists.txt ..
```
And type `make` to start making process.

**It's all!** Compiled files will be placed in the build directory with cloned repo.
