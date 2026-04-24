import os
import re
from pypdf import PdfReader
from typing import List, Dict, Any

# Definição das Expressões Regulares (Regex)
# Capturar o que vem logo depois de "Aluno: "
# O re.DOTALL é necessário para que a busca funcione em todo o texto,
# caso haja quebras de linha inesperadas antes da nota.
PADRAO_NOME = re.compile(r"Aluno: (.+?)\n", re.IGNORECASE | re.DOTALL) 

# NOTA: O '(.+?)\n' captura o nome (não-guloso) até a primeira quebra de linha.
# Apenas para capturar o que vem logo depois de "Sua Nota foi: "
PADRAO_NOTA = re.compile(r"Sua Nota foi: (\d+,\d{2})", re.IGNORECASE)

def extrair_dados_pdf(caminho_arquivo: str) -> str | None:
    # Extrai o nome do aluno e a nota de um único arquivo PDF.
    try:
        reader = PdfReader(caminho_arquivo)
        page = reader.pages[0]
        texto = page.extract_text()
        
        # --- Extração do Nome (Independente) ---
        match_nome = PADRAO_NOME.search(texto)
        aluno = None
        if match_nome:
            # Captura o primeiro grupo e limpa espaços/quebras de linha extras
            aluno = match_nome.group(1).strip() 
            # Se a quebra de linha não estiver exatamente após o nome, o .strip() limpa.
            
        # --- Extração da Nota (Independente) ---
        match_nota = PADRAO_NOTA.search(texto)
        nota = None
        if match_nota:
            # Captura o valor da nota
            nota = match_nota.group(1)

        # Retorna apenas se AMBOS os campos foram encontrados
        if aluno and nota:
            return "Nome: " + aluno + " -- Nota: " + str(nota) # + " Arquivo " + os.path.basename(caminho_arquivo)
        
        # Se um dos campos não for encontrado, retorna None (ou dados parciais, se preferir)
        return None

    except Exception as e:
        print(f"Erro ao processar o arquivo {caminho_arquivo}: {e}")
        return None

def gerar_listagem(diretorio: str) -> List[str]:
    # Percorre todos os PDFs no diretório e extrai os dados.
    lista_alunos = []
    for nome_arquivo in os.listdir(diretorio):
        if nome_arquivo.lower().endswith('.pdf'):
            caminho_completo = os.path.join(diretorio, nome_arquivo)
            dados = extrair_dados_pdf(caminho_completo)
            if dados:
                lista_alunos.append(dados)
    return lista_alunos

if __name__ == "__main__":
    DIRETORIO_PDFS = "./robotica"
    if not os.path.exists(DIRETORIO_PDFS):
        print(f"O diretório '{DIRETORIO_PDFS}' não existe.")
    else:
        print(f"Iniciando extração dos dados em '{DIRETORIO_PDFS}'...")
        listagem = gerar_listagem(DIRETORIO_PDFS)
        listagem.sort()
        if listagem:
            print("\n" + "="*50)
            print("LISTAGEM DE ALUNOS E NOTAS")
            print("="*50)
            for aluno in listagem:
                print(aluno)
            print("="*50)
        else:
            print("Nenhum dado de aluno/nota foi encontrado nos arquivos PDF. Verifique se o diretório e os arquivos estão corretos.")
