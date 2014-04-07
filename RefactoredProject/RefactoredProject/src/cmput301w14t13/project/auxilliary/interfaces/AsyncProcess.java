package cmput301w14t13.project.auxilliary.interfaces;
/**
 * 
 * "An asynchronous process is invoked by a one-way operation and the result 
 * and any faults are returned by invoking other one-way operations. The process 
 * result is returned to the caller via a callback operation." 
 * - http://pic.dhe.ibm.com/infocenter/adiehelp/v5r1m1/index.jsp?topic=%2Fcom.ibm.etools.ctc.bpel.doc%2Fconcepts%2Fcsynch.html 
 * 
 * @author nsd
 *
 */
public interface AsyncProcess {
	public void receiveResult(String result);
}
