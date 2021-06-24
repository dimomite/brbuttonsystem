include_directories(${MAIN_PROJECT_PATH}/Inc)

include_directories(${PROJECT_SOURCE_DIR}/googletest/googlemock/include)
include_directories(${PROJECT_SOURCE_DIR}/googletest/googletest/include)

include_directories(${PROJECT_SOURCE_DIR}/mocks)
# target_include_directories

add_library(MainTests STATIC maintests.cpp)
target_link_libraries(MainTests PUBLIC gtest)

add_library(TestJobs STATIC 
            "${MAIN_PROJECT_PATH}/Src/Comm/ConnectionCtrl.c"
            ConnectionCtrlTest.cpp)
target_link_libraries(TestJobs PUBLIC TestMocks gmock gtest)
