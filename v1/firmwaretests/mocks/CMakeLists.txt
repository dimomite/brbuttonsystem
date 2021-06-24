include_directories(${MAIN_PROJECT_PATH}/Inc)
include_directories(${PROJECT_SOURCE_DIR}/googletest/googlemock/include)
include_directories(${PROJECT_SOURCE_DIR}/googletest/googletest/include)
# target_include_directories

add_library(TestMocks STATIC BtCtrlMock.cpp)
    # "${MAIN_PROJECT_PATH}/Src/Comm/ConnectionCtrl.c"

target_link_libraries(TestMocks PUBLIC gmock gtest)
