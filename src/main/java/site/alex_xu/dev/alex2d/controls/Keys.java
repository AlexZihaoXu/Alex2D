package site.alex_xu.dev.alex2d.controls;

import static org.lwjgl.glfw.GLFW.*;

public class Keys {
    boolean[] keys = new boolean[348];
    static String[] names = new String[] {
            null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, "Space", null, null, null, null, null,
            null, "Apostrophe", null, null, null, null, "Comma", "Minus", "Period", "Slash",
            "Key0", "Key1", "Key2", "Key3", "Key4", "Key5", "Key6", "Key7", "Key8", "Key9",
            null, "Semicolon", null, "Equal", null, null, null, "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z", "LeftBracket", "Backslash", "RightBracket",
            null, null, "GraveAccent", null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, "World1", "World2", null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, "Escape", "Enter",
            "Tab", "Backspace", "Insert", "Delete", "Right", "Left", "Down", "Up", "PageUp",
            "PageDown", "Home", "End", null, null, null, null, null, null, null, null,
            null, null, "CapsLock", "ScrollLock", "NumLock", "PrintScreen", "Pause",
            null, null, null, null, null, "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8",
            "F9", "F10", "F11", "F12", "F13", "F14", "F15", "F16", "F17", "F18", "F19",
            "F20", "F21", "F22", "F23", "F24", "F25", null, null, null, null, null, "Kp0",
            "Kp1", "Kp2", "Kp3", "Kp4", "Kp5", "Kp6", "Kp7", "Kp8", "Kp9", "KpDecimal",
            "KpDivide", "KpMultiply", "KpSubtract", "KpAdd", "KpEnter", "KpEqual", null,
            null, null, "LeftShift", "LeftControl", "LeftAlt", "LeftSuper", "RightShift",
            "RightControl", "RightAlt", "RightSuper"
    };

    static String getName(int key) {
        if (0 <= key && key < names.length) {
            return names[key];
        }
        return null;
    }


    public boolean space() {
        return keys[GLFW_KEY_SPACE];
    }

    public boolean apostrophe() {
        return keys[GLFW_KEY_APOSTROPHE];
    }

    public boolean comma() {
        return keys[GLFW_KEY_COMMA];
    }

    public boolean minus() {
        return keys[GLFW_KEY_MINUS];
    }

    public boolean period() {
        return keys[GLFW_KEY_PERIOD];
    }

    public boolean slash() {
        return keys[GLFW_KEY_SLASH];
    }

    public boolean key0() {
        return keys[GLFW_KEY_0];
    }

    public boolean key1() {
        return keys[GLFW_KEY_1];
    }

    public boolean key2() {
        return keys[GLFW_KEY_2];
    }

    public boolean key3() {
        return keys[GLFW_KEY_3];
    }

    public boolean key4() {
        return keys[GLFW_KEY_4];
    }

    public boolean key5() {
        return keys[GLFW_KEY_5];
    }

    public boolean key6() {
        return keys[GLFW_KEY_6];
    }

    public boolean key7() {
        return keys[GLFW_KEY_7];
    }

    public boolean key8() {
        return keys[GLFW_KEY_8];
    }

    public boolean key9() {
        return keys[GLFW_KEY_9];
    }

    public boolean semicolon() {
        return keys[GLFW_KEY_SEMICOLON];
    }

    public boolean equal() {
        return keys[GLFW_KEY_EQUAL];
    }

    public boolean a() {
        return keys[GLFW_KEY_A];
    }

    public boolean b() {
        return keys[GLFW_KEY_B];
    }

    public boolean c() {
        return keys[GLFW_KEY_C];
    }

    public boolean d() {
        return keys[GLFW_KEY_D];
    }

    public boolean e() {
        return keys[GLFW_KEY_E];
    }

    public boolean f() {
        return keys[GLFW_KEY_F];
    }

    public boolean g() {
        return keys[GLFW_KEY_G];
    }

    public boolean h() {
        return keys[GLFW_KEY_H];
    }

    public boolean i() {
        return keys[GLFW_KEY_I];
    }

    public boolean j() {
        return keys[GLFW_KEY_J];
    }

    public boolean k() {
        return keys[GLFW_KEY_K];
    }

    public boolean l() {
        return keys[GLFW_KEY_L];
    }

    public boolean m() {
        return keys[GLFW_KEY_M];
    }

    public boolean n() {
        return keys[GLFW_KEY_N];
    }

    public boolean o() {
        return keys[GLFW_KEY_O];
    }

    public boolean p() {
        return keys[GLFW_KEY_P];
    }

    public boolean q() {
        return keys[GLFW_KEY_Q];
    }

    public boolean r() {
        return keys[GLFW_KEY_R];
    }

    public boolean s() {
        return keys[GLFW_KEY_S];
    }

    public boolean t() {
        return keys[GLFW_KEY_T];
    }

    public boolean u() {
        return keys[GLFW_KEY_U];
    }

    public boolean v() {
        return keys[GLFW_KEY_V];
    }

    public boolean w() {
        return keys[GLFW_KEY_W];
    }

    public boolean x() {
        return keys[GLFW_KEY_X];
    }

    public boolean y() {
        return keys[GLFW_KEY_Y];
    }

    public boolean z() {
        return keys[GLFW_KEY_Z];
    }

    public boolean leftBracket() {
        return keys[GLFW_KEY_LEFT_BRACKET];
    }

    public boolean backslash() {
        return keys[GLFW_KEY_BACKSLASH];
    }

    public boolean rightBracket() {
        return keys[GLFW_KEY_RIGHT_BRACKET];
    }

    public boolean graveAccent() {
        return keys[GLFW_KEY_GRAVE_ACCENT];
    }

    public boolean world1() {
        return keys[GLFW_KEY_WORLD_1];
    }

    public boolean world2() {
        return keys[GLFW_KEY_WORLD_2];
    }

    public boolean escape() {
        return keys[GLFW_KEY_ESCAPE];
    }

    public boolean enter() {
        return keys[GLFW_KEY_ENTER];
    }

    public boolean tab() {
        return keys[GLFW_KEY_TAB];
    }

    public boolean backspace() {
        return keys[GLFW_KEY_BACKSPACE];
    }

    public boolean insert() {
        return keys[GLFW_KEY_INSERT];
    }

    public boolean delete() {
        return keys[GLFW_KEY_DELETE];
    }

    public boolean right() {
        return keys[GLFW_KEY_RIGHT];
    }

    public boolean left() {
        return keys[GLFW_KEY_LEFT];
    }

    public boolean down() {
        return keys[GLFW_KEY_DOWN];
    }

    public boolean up() {
        return keys[GLFW_KEY_UP];
    }

    public boolean pageUp() {
        return keys[GLFW_KEY_PAGE_UP];
    }

    public boolean pageDown() {
        return keys[GLFW_KEY_PAGE_DOWN];
    }

    public boolean home() {
        return keys[GLFW_KEY_HOME];
    }

    public boolean end() {
        return keys[GLFW_KEY_END];
    }

    public boolean capsLock() {
        return keys[GLFW_KEY_CAPS_LOCK];
    }

    public boolean scrollLock() {
        return keys[GLFW_KEY_SCROLL_LOCK];
    }

    public boolean numLock() {
        return keys[GLFW_KEY_NUM_LOCK];
    }

    public boolean printScreen() {
        return keys[GLFW_KEY_PRINT_SCREEN];
    }

    public boolean pause() {
        return keys[GLFW_KEY_PAUSE];
    }

    public boolean f1() {
        return keys[GLFW_KEY_F1];
    }

    public boolean f2() {
        return keys[GLFW_KEY_F2];
    }

    public boolean f3() {
        return keys[GLFW_KEY_F3];
    }

    public boolean f4() {
        return keys[GLFW_KEY_F4];
    }

    public boolean f5() {
        return keys[GLFW_KEY_F5];
    }

    public boolean f6() {
        return keys[GLFW_KEY_F6];
    }

    public boolean f7() {
        return keys[GLFW_KEY_F7];
    }

    public boolean f8() {
        return keys[GLFW_KEY_F8];
    }

    public boolean f9() {
        return keys[GLFW_KEY_F9];
    }

    public boolean f10() {
        return keys[GLFW_KEY_F10];
    }

    public boolean f11() {
        return keys[GLFW_KEY_F11];
    }

    public boolean f12() {
        return keys[GLFW_KEY_F12];
    }

    public boolean f13() {
        return keys[GLFW_KEY_F13];
    }

    public boolean f14() {
        return keys[GLFW_KEY_F14];
    }

    public boolean f15() {
        return keys[GLFW_KEY_F15];
    }

    public boolean f16() {
        return keys[GLFW_KEY_F16];
    }

    public boolean f17() {
        return keys[GLFW_KEY_F17];
    }

    public boolean f18() {
        return keys[GLFW_KEY_F18];
    }

    public boolean f19() {
        return keys[GLFW_KEY_F19];
    }

    public boolean f20() {
        return keys[GLFW_KEY_F20];
    }

    public boolean f21() {
        return keys[GLFW_KEY_F21];
    }

    public boolean f22() {
        return keys[GLFW_KEY_F22];
    }

    public boolean f23() {
        return keys[GLFW_KEY_F23];
    }

    public boolean f24() {
        return keys[GLFW_KEY_F24];
    }

    public boolean f25() {
        return keys[GLFW_KEY_F25];
    }

    public boolean kp0() {
        return keys[GLFW_KEY_KP_0];
    }

    public boolean kp1() {
        return keys[GLFW_KEY_KP_1];
    }

    public boolean kp2() {
        return keys[GLFW_KEY_KP_2];
    }

    public boolean kp3() {
        return keys[GLFW_KEY_KP_3];
    }

    public boolean kp4() {
        return keys[GLFW_KEY_KP_4];
    }

    public boolean kp5() {
        return keys[GLFW_KEY_KP_5];
    }

    public boolean kp6() {
        return keys[GLFW_KEY_KP_6];
    }

    public boolean kp7() {
        return keys[GLFW_KEY_KP_7];
    }

    public boolean kp8() {
        return keys[GLFW_KEY_KP_8];
    }

    public boolean kp9() {
        return keys[GLFW_KEY_KP_9];
    }

    public boolean kpDecimal() {
        return keys[GLFW_KEY_KP_DECIMAL];
    }

    public boolean kpDivide() {
        return keys[GLFW_KEY_KP_DIVIDE];
    }

    public boolean kpMultiply() {
        return keys[GLFW_KEY_KP_MULTIPLY];
    }

    public boolean kpSubtract() {
        return keys[GLFW_KEY_KP_SUBTRACT];
    }

    public boolean kpAdd() {
        return keys[GLFW_KEY_KP_ADD];
    }

    public boolean kpEnter() {
        return keys[GLFW_KEY_KP_ENTER];
    }

    public boolean kpEqual() {
        return keys[GLFW_KEY_KP_EQUAL];
    }

    public boolean leftShift() {
        return keys[GLFW_KEY_LEFT_SHIFT];
    }

    public boolean leftControl() {
        return keys[GLFW_KEY_LEFT_CONTROL];
    }

    public boolean leftAlt() {
        return keys[GLFW_KEY_LEFT_ALT];
    }

    public boolean leftSuper() {
        return keys[GLFW_KEY_LEFT_SUPER];
    }

    public boolean rightShift() {
        return keys[GLFW_KEY_RIGHT_SHIFT];
    }

    public boolean rightControl() {
        return keys[GLFW_KEY_RIGHT_CONTROL];
    }

    public boolean rightAlt() {
        return keys[GLFW_KEY_RIGHT_ALT];
    }

    public boolean rightSuper() {
        return keys[GLFW_KEY_RIGHT_SUPER];
    }

    public boolean menu() {
        return keys[GLFW_KEY_MENU];
    }

    void updateKeys(int key, boolean pressed) {
        keys[key] = pressed;
    }
}
