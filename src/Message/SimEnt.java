
package Message;


public abstract class SimEnt {

    protected SimEnt() {
        
        Scheduler.instance().birthSimEnt(this);
    }

    protected final void suicide() {
        this.destructor();
        Scheduler.instance().deathSimEnt(this);
    }

    protected void destructor() {
    }

    protected final Scheduler.EventHandle send(SimEnt dst, Event ev, double t) {
        return Scheduler.instance().register(this, dst, ev, t);
    }

    protected void revokeSend(Scheduler.EventHandle h) {
        Scheduler.instance().deregister(h);
    }

    public abstract void recv(SimEnt src, Event ev);

    public abstract void deliveryAck(Scheduler.EventHandle h);

}
