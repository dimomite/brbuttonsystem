cmake_minimum_required(VERSION 3.20)

project(br_button_fw_tests)

enable_language(C)
enable_language(CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

set(MAIN_PROJECT_PATH ${PROJECT_SOURCE_DIR}/../firm)

# add_subdirectory(${PROJECT_SOURCE_DIR}/cpputest)
add_subdirectory(${PROJECT_SOURCE_DIR}/googletest)
add_subdirectory(${PROJECT_SOURCE_DIR}/mocks)
add_subdirectory(${PROJECT_SOURCE_DIR}/tests)

include_directories(${MAIN_PROJECT_PATH}/Inc)
include_directories(${PROJECT_SOURCE_DIR}/googletest/googletest/include)
include_directories(${PROJECT_SOURCE_DIR}/googletest/googlemock/include)

link_directories(${PROJECT_SOURCE_DIR}/googletest/build/lib) # TODO I don't like this

# target_include_directories(... PUBLIC ...)

# include(CTest)
enable_testing()

add_executable(mytests mytests.cpp)

target_link_libraries(mytests PRIVATE gtest gmock
                    TestMocks
                    MainTests
                    TestJobs
                    )

add_test(MainTestsCases mytests)
add_test(ConnectionControllerTest mytests)
