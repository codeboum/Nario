package kompositum;

public abstract class ListenElement {
	public abstract Knoten adden(Knoten kNeu);
	public abstract DatenElement gib(int index);
	public abstract int gibRestLaenge();
	public abstract void ausgeben();
}