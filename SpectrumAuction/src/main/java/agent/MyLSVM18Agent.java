package agent;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import org.json.simple.parser.ParseException;

import brown.communication.messages.ITradeMessage;
import brown.simulations.OfflineSimulation;
import brown.system.setup.library.Setup;
import brown.user.agent.IAgent;
import brown.user.agent.library.AbsLSVM18Agent;
import brown.user.agent.library.OnlineAgentBackend;

public class MyLSVM18Agent extends AbsLSVM18Agent implements IAgent {
	private final static String NAME = "NapNat"; // TODO: give your agent a name.
	public MyLSVM18Agent(String name) {
		super(name);
		// TODO: fill this in (if necessary)
	}
	
	@Override
	protected Map<String, Double> getBids(Map<String, Double> minBids) {
		// TODO: fill this in
		System.out.println(minBids);
		Map<String, Double> returnBid = new HashMap<String, Double>();
		System.out.println(this.getProximity());
		for (String g: this.getProximity()) {
			Set<String> cart = new HashSet<String>();
			cart.add(g);
			returnBid.put(g, 10000.0); //this.getValuation(g));
		}

		for (int i = 0; i < 10; i++) {
			//IBidVector b = bInit.copy();
			
			for (String gi : this.getProximity()) {
				// TODO: calculate the marginal value of gi, and update b with it (b.setBid(...)).
				// use MarginalValues.calcMarginalValue (your implementation!)
				double MV = calcMarginalValue(this.getProximity(), gi, returnBid, minBids);
				returnBid.put(gi, this.clipBid(gi, MV* (this.getCurrentRound() + 1)/(this.getCurrentRound() + 2), minBids));
			}
		}
		//System.out.println(bInit);
		System.out.println(returnBid);
		return returnBid;
	}

	@Override
	protected void onAuctionStart() {
		// TODO: fill this in (if necessary)

	}

	@Override
	protected void onAuctionEnd(Map<Integer, Set<String>> allocations, Map<Integer, Double> payments,
			List<List<ITradeMessage>> tradeHistory) {
		// TODO: fill this in (if necessary)

	}

	public double calcMarginalValue(Set<String> G, String good, Map<String, Double> b, Map<String, Double> p) {
		// TODO: fill this in
		Set<String> cart = new HashSet<String>();
		for (String g: G) {
			if (!g.equals(good)) {
				double price = p.get(g);
				double bid = b.get(g);
				if (bid > price) {
					cart.add(good);
				}
			}
		}
		double back = this.getValuation(cart);
		cart.add(good);
		double front = this.getValuation(cart);
		return front - back;
	}

	public static void main(String[] args) throws InterruptedException {
		if (args.length == 0) {
			// Don't change this.
			new OfflineSimulation("offline_test_config.json", "input_configs/lsvm_smra_offline.json", "outfile", false).run();
		} else {
			// Don't change this.
			MyLSVM18Agent agent = new MyLSVM18Agent(NAME);
			agent.addAgentBackend(new OnlineAgentBackend("localhost", Integer.parseInt(args[0]), new Setup(), agent));
			while (true) {
			}
		}
	}

}
