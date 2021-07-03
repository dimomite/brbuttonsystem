#include "gmock/gmock.h"

#include <cstdint>
#include "BaseMock.h"

extern "C"
{
#include "Comm/RemoteMessagesProcessor.h"
}

class RemoteMessagesProcessorMock : public BaseMock<RemoteMessagesProcessor_t, RemoteMessagesProcessorMock>
{
public:
    MOCK_METHOD(void, handleRemoteData, (RemoveMessageSource source, int8_t *data));
};

void mess_handleRemoteData(RemoteMessagesProcessor_t *processor, RemoveMessageSource source, int8_t *data)
{
    auto&& mock = BaseMock<RemoteMessagesProcessor_t, RemoteMessagesProcessorMock>::restore(processor);
    mock->handleRemoteData(source, data);
}
