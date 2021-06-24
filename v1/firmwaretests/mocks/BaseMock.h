#pragma once

#include <memory>
#include <type_traits>
#include <unordered_map>

template <typename TCtrl, typename TMock>
class BaseMock
{
    std::unique_ptr<TCtrl> base;

    inline static std::unordered_map<TCtrl *, TMock *> instances;

    inline TMock *asChild() { return static_cast<TMock *>(this); }

public:
    // static_assert(std::is_same<std::decay<TCtrl>, TCtrl>::value, "Type should be in a \"raw\" form"); // FIXME restore

    inline BaseMock();
    virtual ~BaseMock() {}

    inline TCtrl *raw() { return this->base.get(); }

    static TMock *restore(TCtrl *rawPtr);
};

template <typename TCtrl, typename TMock>
BaseMock<TCtrl, TMock>::BaseMock()
    : base(std::make_unique<TCtrl>())
{
    instances[raw()] = asChild(); // FIXME very bad
}

template <typename TCtrl, typename TMock>
std::unordered_map<TCtrl *, TMock *> instances;

template <typename TCtrl, typename TMock>
TMock *BaseMock<TCtrl, TMock>::restore(TCtrl *rawPtr)
{
    return instances[rawPtr];
}
