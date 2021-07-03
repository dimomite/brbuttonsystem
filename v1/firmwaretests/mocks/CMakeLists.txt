include_directories(${MAIN_PROJECT_PATH}/Inc)
include_directories(${PROJECT_SOURCE_DIR}/googletest/googlemock/include)
include_directories(${PROJECT_SOURCE_DIR}/googletest/googletest/include)
# target_include_directories

add_library(TestMocks STATIC 
            BtCtrlMock.cpp
            RemoteMessagesProcessorMock.cpp
            )

target_link_libraries(TestMocks PUBLIC gmock gtest)
