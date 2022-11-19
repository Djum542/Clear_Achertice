/*
*  created date: Nov 18, 2022
*  author: cgm
*/
package tools;

import java.util.LinkedList;
import java.util.List;

public class Publisher {
    private List<Subscriber> subscribers = new LinkedList<Subscriber>();
	public void subscribe(Subscriber s) { subscribers.add(s); }
	public void unsubscribe(Subscriber s) { subscribers.remove(s); }
	public void notifySubscribers() {
		for(Subscriber s: subscribers) s.update();
	}
}
