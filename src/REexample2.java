import java.util.ArrayList;

public class REexample2 {


	/**
	 * has an ordered list of children
	 * 
	 * @author Tomer
	 *
	 */
	public static class Node {
		public static enum NodeType {
			OR, AND, KLEENE, KLEENE_P, EXCEPT, STUB, LEAF;
		}

		public final NodeType type;
		public final ArrayList<Node> children;

		public Node(final NodeType t,final Node ... nodes) {
			children = new ArrayList<>();
			for (Node node : nodes)
				children.add(node);
			type = t;
		}

		public Node then(final Node sibling) {
			if (type != NodeType.STUB)
				return new Node(NodeType.STUB, this, sibling);
			children.add(sibling);
			return this;
		}
	}

	public static class Leaf extends Node {

		public static enum LeafType {
			WORD, Chars
		}

		public final Object content;
		public final LeafType leafType;

		public Leaf(final LeafType leafType, final Object content) {
			super(NodeType.LEAF, new Node[] {});
			this.content = content;
			this.leafType = leafType;
		}
	}

	public static void main(final String args[]) {
		matchRE("Za", or(range('A', 'Z'), range('a', 'z')));

		matchRE("Bs", and(range('a', 'z'), range('b', 'c')));

		matchRE("AAA", kleeneP(range('a', 'z')));

		matchRE("ASDSD", or(chars('a', 'b', '[', ']', '3'), range('A', 'V')));

		matchRE("ay", range('a', 'c').then(except(chars('^', '#', 'A'))));

		Node m = word("http://").then(or(range('a', 'z'), range('A', 'Z')));
		matchRE("http://WebSitE", m);

	}

	private static Leaf word(final String s) {
		return new Leaf(Leaf.LeafType.WORD, s);
	}

	private static Node kleeneP(final Node n) {
		return new Node(Node.NodeType.KLEENE_P, n);
	}

	private static Node kleene(final Node n) {
		return new Node(Node.NodeType.KLEENE, n);
	}

	private static Node or(final Node... nodes) {
		return new Node(Node.NodeType.OR, nodes);
	}

	private static Node and(final Node... nodes) {
		return new Node(Node.NodeType.AND, nodes);
	}

	private static Leaf range(final char from, final char to) {
		char chars[] = new char[to-from+1];
		for (int i=0;i<to-from+1;i++)
			chars[i] = (char)(i+from);
		return new Leaf(Leaf.LeafType.Chars, chars);
	}

	private static Node except(final Node n) {
		return new Node(Node.NodeType.EXCEPT, n);
	}

	public static Leaf chars(final char... cs) {
		return new Leaf(Leaf.LeafType.Chars, cs);
	}


	/**
	 * This is the only function that needs to be implemented by the "user" This
	 * is the "semantic parser" of the syntex tree denoted by nodes.
	 **/
	public static boolean matchRE(final String s, final Node matcher) {
		return true;
	}

}
