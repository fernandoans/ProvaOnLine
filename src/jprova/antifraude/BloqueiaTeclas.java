package jprova.antifraude;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import com.sun.jna.platform.win32.WinUser.MSG;



public class BloqueiaTeclas {
    private static HHOOK hhk;
    private static LowLevelKeyboardProc keyboardHook;
    private static User32 lib;

    public static void bloquear() {
        if (verificaSeWindows()) {
            new Thread(() -> {
                lib = User32.INSTANCE;
                HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);

                keyboardHook = (nCode, wParam, info) -> {
                    if (nCode >= 0) {
                        switch (info.vkCode) {

                            // Para adicionar ou remover qualquer tecla basta
                            // modificar abaixo usando: case (hexadecimal da tecla):

                            case 0x5B: // Tecla Windows esquerda
                            case 0x5C: // Tecla Windows direita
                            case 0x09: // Tecla Tab
                            case 0x27:  // Tecla ["'] (Apóstrofo)
                            case 0x12: // Tecla alt

                                return new LRESULT(1); // Bloqueia a tecla
                            default:
                                break; // Continua com o processamento normal
                        }
                    }
                    return lib.CallNextHookEx(hhk, nCode, wParam, new WinDef.LPARAM());
                };

                hhk = lib.SetWindowsHookEx(WH_KEYBOARD_LL, keyboardHook, hMod, 0);

                // Loop para receber mensagens
                MSG msg = new MSG();
                while (lib.GetMessage(msg, null, 0, 0) != 0) {
                    lib.TranslateMessage(msg);
                    lib.DispatchMessage(msg);
                }

                // Remove o hook
                lib.UnhookWindowsHookEx(hhk);
            }).start();
        }
    }

    public static boolean verificaSeWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win");
    }

    private static final int WH_KEYBOARD_LL = 13;
}
