import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.auction.value.valuation.IGeneralValuation;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;
import brown.user.agent.library.localbid.*;

public class LocalBid {
	private static final int NUM_ITERATIONS = 10;
	private static final IGeneralValuation VALUATION = SampleValuations.SUBSTITUTE_VALUATION; // TODO: run with other valuations too!
	
	
	public static IBidVector localBid(Set<IItem> G, IGeneralValuation v, ILinearPrices p, int numIterations) {
		IBidVector bInit = new BidVector();
		
		// TODO: populate bInit with a bid for each good in G.
		for (IItem i : G) {
			ICart cart = new Cart();
			cart.addToCart(i);
			bInit.setBid(i, v.getValuation(cart));
		}
		
		// Print initial bid vector
		System.out.println(bInit);
		
		for (int i = 0; i < numIterations; i++) {
			//IBidVector b = bInit.copy();
			
			for (IItem gi : G) {
				// TODO: calculate the marginal value of gi, and update b with it (b.setBid(...)).
				// use MarginalValues.calcMarginalValue (your implementation!)
				double MV = MarginalValues.calcMarginalValue(G, gi, v, bInit, p);
				bInit.setBid(gi, MV);
			}
			
			// TODO (optional): test for convergence between bInit and b.
			
			// TODO: update bInit.
			
			// Print out the latest bid vector.
			System.out.println(bInit);
		}
		//System.out.println(bInit);
		return bInit;
	}

	public static void main(String[] args) {
		Set<IItem> G = SampleValuations.G();
		ILinearPrices p = SampleValuations.genPriceVector();
		localBid(G, VALUATION, p, NUM_ITERATIONS);
	}
}
