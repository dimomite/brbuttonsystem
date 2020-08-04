#include "EntertainmentCtrl.h"

#include "DeviceMacros.h"

SoundProfile_t sp;
static uint8_t durationCounter;

static LightProfile_t lp;

void ent_onSystemTick(EntertainmentCtrl_t *ec)
{
    if (sp.repetitions > 0) {
        if (durationCounter > 0) {
            --durationCounter;
            if (0 == durationCounter) {
                --sp.repetitions;
                durationCounter = sp.duration;
                if (sp.repetitions & 1) {
                    startSound(sp.freq);
                } else {
                    stopSound();
                }

                // if (0 == sp.repetitions) stopLight(); // TODO debug only
            }
        }
    }
}

void ent_startSound(EntertainmentCtrl_t *ec, SoundProfile_t const *soundProfile)
{
    ent_stopSound(ec);

    sp = *soundProfile;
    if (0 == sp.repetitions) return;
    if (0 == (sp.repetitions & 1)) --sp.repetitions; // decrement for even numbers

    // no check for duration allows to do non-stop signal when value is 0
    durationCounter = sp.duration;

    startSound(soundProfile->freq);
}

void ent_stopSound(EntertainmentCtrl_t *ec)
{
    stopSound();
}

void ent_startLight(EntertainmentCtrl_t *ec, LightProfile_t const *lightProfile)
{
    ent_stopLight(ec);

    lp = *lightProfile;
    if (0 == lp.intensity) return;

    startLight(lp.intensity);
}

void ent_stopLight(EntertainmentCtrl_t *ec)
{
    stopLight();
}
