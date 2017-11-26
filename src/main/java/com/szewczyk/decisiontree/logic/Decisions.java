package logic; /**
 *
 */

import utils.BadDecisionException;

import java.util.HashMap;
import java.util.Map;


class  Decisions {
  private Map<String, Attribute> decisions;

  public Decisions() {
    decisions = new HashMap<String, Attribute>();
  }

  public Map<String, Attribute> getMap() {
    return decisions;
  }

  public void put(String decision, Attribute attribute) {
    decisions.put(decision, attribute);
  }

  public void clear() {
    decisions.clear();
  }
  public Attribute apply(String value) throws BadDecisionException {
    Attribute result = decisions.get(value);

    if ( result == null )
      throw new BadDecisionException();

    return result;
  }
}
