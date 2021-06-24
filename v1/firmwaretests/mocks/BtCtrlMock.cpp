#include "gmock/gmock.h"

#include "BaseMock.h"

extern "C" {
#include "DAL/BtCtrl.h"
}

class BtCtrlMock : public BaseMock<BtCtrl_t, BtCtrlMock>
{
public:
    MOCK_METHOD(void, connect, ());
    MOCK_METHOD(void, disconnect, ());
};

void btctrl_connect(BtCtrl_t *bc)
{
    auto&& mock = BaseMock<BtCtrl_t, BtCtrlMock>::restore(bc);
    mock->connect();
}

void btctrl_disconnect(BtCtrl_t *bc)
{
    auto&& mock = BaseMock<BtCtrl_t, BtCtrlMock>::restore(bc);
    mock->disconnect();
}
