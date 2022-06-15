cmake_minimum_required(VERSION 3.5)

set (CMAKE_C_FLAGS "-O2 -s")


if(CMAKE_SOURCE_DIR STREQUAL CMAKE_BINARY_DIR)
    message(FATAL_ERROR "In-source builds not allowed. Please run \"cmake ..\" from the build directory. You may need to delete \"${CMAKE_SOURCE_DIR}/CMakeCache.txt\" first.")
endif()


project(BetterCC C)

set(CMAKE_INCLUDE_CURRENT_DIR ON)

file(GLOB DEVICES_SRC
	"devices/*.h"
	"devices/*.c"
)

add_library(bettercc SHARED
	cJSON/cJSON.h
	cJSON/cJSON.c
	bettercc.h
	bettercc.c
	bettercc-private.h
	bettercc-private.c
	${DEVICES_SRC}
	)



project(BetterCC_apps C)

add_executable(redstone redstone.c)

add_executable(fimexu fimexu.c)

add_executable(seplay seplay.c)

add_executable(lshldev lshldev.c)

target_link_libraries(redstone bettercc)

target_link_libraries(fimexu bettercc)

target_link_libraries(seplay bettercc)

target_link_libraries(lshldev bettercc)

