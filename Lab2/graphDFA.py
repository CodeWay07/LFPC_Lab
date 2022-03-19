import networkx as nx
import matplotlib.pyplot as plt


def drawDFA():
    G = nx.DiGraph()
    G.add_edges_from(
        [('q0', 'q1'), ('q0', 'q0'), ('q1', 'q2q3'), ('q2q3', 'q2q3'),
         ('q2q3', 'q0')])

    val_map = {'q3': 0.3
               }

    values = [val_map.get(node, 0.75) for node in G.nodes()]

    back_edge = [('q0', 'q1'), ('q1', 'q2q3'), ('q2q3', 'q0')]
    regular_edges = [edge for edge in G.edges() if edge not in back_edge]
    pos = nx.spring_layout(G)
    nx.draw_networkx_nodes(G, pos, node_color=values, node_size=500)
    nx.draw_networkx_labels(G, pos)
    nx.draw_networkx_edge_labels(G, pos, edge_labels=dict([
        (('q0', 'q1'), 'a'),
        (('q0', 'q0'), 'b'),
        (('q1', 'q2q3'), 'a'),
        (('q2q3', 'q2q3'), 'a'),
        (('q2q3', 'q0'), 'b')
    ])
                                 )

    nx.draw_networkx_edges(G, pos, edgelist=back_edge, arrows=True, connectionstyle="arc3,rad=0.3")
    nx.draw_networkx_edges(G, pos, edgelist=regular_edges, arrows=True)
    plt.title(label= 'DFA')
    plt.show()