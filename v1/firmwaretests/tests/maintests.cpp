#include "myfunc.h"
#include "gtest/gtest.h"

TEST(MainTestsCases, testAdd)
{
    ASSERT_EQ(5, add(2, 3)) << "Wrong addition result";
}

TEST(MainTestsCases, failedTestAdd)
{
    ASSERT_NE(4, add(2, 3)) << "Correct addition result";
}
