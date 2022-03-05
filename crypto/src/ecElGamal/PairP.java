package ecElGamal;
public class PairP<L,R> {
    public L left;
    public R right;

    public PairP(L left, R right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int hashCode() { return left.hashCode() ^ right.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PairP)) return false;
        PairP<?, ?> pair = (PairP<?, ?>) o;
        return left.equals(pair.left) && right.equals(pair.right);
    }

    @Override
    public String toString() {
        return "<" + left.toString() + ", " + right.toString() + ">";
    }
}