package jprova;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.HashSet;

public class VerProcs {
	private Set<String> procsIniciais = new HashSet<>();
    private Set<String> procsAtuais = new HashSet<>();
    public static final int SO;
    private static final String comando;

    static {
        SO = testarSO();
        if (SO == 0) {
            comando = System.getenv("windir") +"\\system32\\"+"tasklist.exe";   
        } else {
            comando = "ps -e";
        }
    }
    
    private static int testarSO() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return 0;
        } 
        if (os.contains("mac") || os.contains("nix") || os.contains("nux")) {
            return 1;
        } 
        return -1;
    }
    
    public void iniciarContagem() {
		obterProcs(procsIniciais);
    }
    
    public void procsAtuais() {
    	obterProcs(procsAtuais);
    }
    
    public boolean isDiferente() {
    	return procsIniciais.size() != procsAtuais.size();
    }
    
    private void obterProcs(Set<String> procs) {
        String line;
        Process p;
        try {
            procs.clear();
            p = Runtime.getRuntime().exec(comando);   
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if(parts.length > 1) {
                    procs.add(parts[SO == 0?0:4]);
                }
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
