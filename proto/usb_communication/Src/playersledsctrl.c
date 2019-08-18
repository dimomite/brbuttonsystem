/*
 * playersledsctrl.c
 *
 *  Created on: 18 авг. 2019 г.
 *      Author: DiMomite
 */

#include "playersledsctrl.h"

#define PLAYER_1_BUTTON_LED (1 << 0)
#define PLAYER_2_BUTTON_LED (1 << 1)
#define PLAYER_3_BUTTON_LED (1 << 2)
#define PLAYER_4_BUTTON_LED (1 << 3)

#define PLAYER_1_SYSTEM_LED (1 << 4)
#define PLAYER_2_SYSTEM_LED (1 << 5)
#define PLAYER_3_SYSTEM_LED (1 << 6)
#define PLAYER_4_SYSTEM_LED (1 << 7)

void PlayerLeds_Init(PlayerLedsCtrl* ctrl, LedsDataSender sender) {
    if (!ctrl) return;
    ctrl->sender = sender;

    PlayerLeds_ClearAll(ctrl);
}

void PlayerLeds_ClearAll(PlayerLedsCtrl* ctrl) {
    if (!ctrl) return;

    ctrl->state = 0;
    if (ctrl->sender) ctrl->sender(ctrl->state);
}

void PlayerLeds_SetPlayer(PlayerLedsCtrl* ctrl, Players player, PlayerVisMode mode) {
    if (!ctrl) return;
    uint8_t mask;
    if (mode & PLAYER_VIS_ONLY_BUTTON_LIGHT) {
        switch (player) {
        case PLAYER_1:
            mask = PLAYER_1_BUTTON_LED;
            break;
        case PLAYER_2:
            mask = PLAYER_2_BUTTON_LED;
            break;
        case PLAYER_3:
            mask = PLAYER_3_BUTTON_LED;
            break;
        case PLAYER_4:
            mask = PLAYER_4_BUTTON_LED;
            break;
        case PLAYER_UNDEFINED:
            break;
        }
    }

    if (mode & PLAYER_VIS_ONLY_SYSTEM) {
        switch (player) {
        case PLAYER_1:
            mask |= PLAYER_1_SYSTEM_LED;
            break;
        case PLAYER_2:
            mask |= PLAYER_2_SYSTEM_LED;
            break;
        case PLAYER_3:
            mask |= PLAYER_3_SYSTEM_LED;
            break;
        case PLAYER_4:
            mask |= PLAYER_4_SYSTEM_LED;
            break;
        case PLAYER_UNDEFINED:
            break;
        }
    }

    if (mask) {
        ctrl->state |= mask;
        if (ctrl->sender) ctrl->sender(ctrl->state);
    }
}

void PlayerLeds_ClearPlayer(PlayerLedsCtrl* ctrl, Players player) {
    if (!ctrl) return;

    uint8_t mask = 0;
    switch (player) {
    case PLAYER_1:
        mask = PLAYER_1_BUTTON_LED | PLAYER_1_SYSTEM_LED;
        break;
    case PLAYER_2:
        mask = PLAYER_2_BUTTON_LED | PLAYER_2_SYSTEM_LED;
        break;
    case PLAYER_3:
        mask = PLAYER_3_BUTTON_LED | PLAYER_3_SYSTEM_LED;
        break;
    case PLAYER_4:
        mask = PLAYER_4_BUTTON_LED | PLAYER_4_SYSTEM_LED;
        break;
    case PLAYER_UNDEFINED:
        break;
    }

    if (mask) {
        ctrl->state &= ~mask;
        if (ctrl->sender) ctrl->sender(ctrl->state);
    }
}

