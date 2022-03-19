# Variant 5
# type 3
grammar = [
    'S->bS',
    'S->aF',
    'S->d',
    'F->cF ',
    'F->dF',
    'F->aL',
    'L->aL',
    'L->c'
]

def parseGrammar(grammar):
    finiteAutomation = {}

    for grammarRules in grammar:
        rule = grammarRules.split('->')
        
        subGrammar = {
            0: list(rule[0])[0],
            1: list(rule[1])
        }  

        if not subGrammar[0] in finiteAutomation:
            finiteAutomation[subGrammar[0]] = {}

        if len(subGrammar[1]) == 1:
            subGrammar[1].append('$')

        finiteAutomation[subGrammar[0]][subGrammar[1][0]] = subGrammar[1][1]

    return finiteAutomation


def testGrammar(fa, initial, final, inputWord):

    state = initial

    currword = ""
    print(state + "->")

    for ch in inputWord:
        if state in fa and ch in fa[state]:
            state = fa[state][ch]
            currword += ch
            generatedWord = currword + state
            print('->' + generatedWord)
        else:
            return False

    return state in final



finiteAutomation = parseGrammar(grammar)
print(finiteAutomation)
while True:
    print('Test String: ')
    inputWord = input()
    if inputWord == '':
        break
    print(testGrammar(finiteAutomation, 'S', {'$'}, inputWord))
    

    
