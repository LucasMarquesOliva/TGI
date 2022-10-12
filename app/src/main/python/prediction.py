# Import das bibliotecas
import pandas as pd
from os.path import dirname, join

# Importando os atributos previsores
filename = join(dirname(__file__), "previsores.csv")
previsores = pd.read_csv(filename, header=None)

# Transformando a estrutura em Array.
previsores = previsores.values

# Importando o atributo alvo
filename = join(dirname(__file__), "alvo.csv")
alvo = pd.read_csv(filename, header=None)

# Transformando a estrutura em Array.
alvo = alvo.iloc[:, 0].values

#CARREGANDO O CLASSIFICADOR RANDOM FOREST
from sklearn.ensemble import RandomForestClassifier
random = RandomForestClassifier(n_estimators=184, criterion='entropy', random_state=6, max_depth=8)
random.fit(previsores, alvo)

def predict(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8):

    paciente = ([[arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8]])

    resultado = random.predict(paciente)

    if resultado == 1:
        return 1
    else:
        return 0


