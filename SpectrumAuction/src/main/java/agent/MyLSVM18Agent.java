package agent;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.parser.ParseException;

import brown.communication.messages.ITradeMessage;
import brown.simulations.OfflineSimulation;
import brown.system.setup.library.Setup;
import brown.user.agent.IAgent;
import brown.user.agent.library.AbsLSVM18Agent;
import brown.user.agent.library.OnlineAgentBackend;

public class MyLSVM18Agent extends AbsLSVM18Agent implements IAgent {
	private final static String NAME = ""; // TODO: give your agent a name.

	public MyLSVM18Agent(String name) {
		super(name);
		// TODO: fill this in (if necessary)

	}
	
	@Override
	protected Map<String, Double> getBids(Map<String, Double> minBids) {
		// TODO: fill this in
		
		return null;
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
