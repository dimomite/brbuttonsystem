#include "gmock/gmock.h"
#include "gtest/gtest.h"

#include "tests/maintests.cpp"
#include "tests/ConnectionCtrlTest.cpp"

int main(int argc, char *argv[])
{
    ::testing::InitGoogleTest(&argc, argv);
    ::testing::InitGoogleMock(&argc, argv);
    return RUN_ALL_TESTS();
}
