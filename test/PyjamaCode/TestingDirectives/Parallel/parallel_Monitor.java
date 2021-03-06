//Pyjama compiler version:v1.5.3
package PyjamaCode.TestingDirectives.Parallel;

import java.util.ArrayList;
import java.util.List;
import Domain.Bean.*;
import pj.Pyjama;
import Utility.MemoryAndCPUHelper.*;

import pj.pr.*;
import pj.PjRuntime;
import pj.Pyjama;
import pi.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.SwingUtilities;
import java.lang.reflect.InvocationTargetException;
import pj.pr.exceptions.OmpParallelRegionLocalCancellationException;

public class parallel_Monitor {

    public List<MonitorInfoBean> parallel_region(int threadNumber, int n) throws Exception {{
        Pyjama.omp_set_num_threads(threadNumber);
        int completeNumber = 0;
        int[] array = new int[n];
        MonitorInfo service = new MonitorInfo();
        List<MonitorInfoBean> result = new ArrayList<MonitorInfoBean>();
        /*OpenMP Parallel region (#0) -- START */
        InternalControlVariables icv_previous__OMP_ParallelRegion_0 = PjRuntime.getCurrentThreadICV();
        InternalControlVariables icv__OMP_ParallelRegion_0 = PjRuntime.inheritICV(icv_previous__OMP_ParallelRegion_0);
        int _threadNum__OMP_ParallelRegion_0 = icv__OMP_ParallelRegion_0.nthreads_var.get(icv__OMP_ParallelRegion_0.levels_var);
        ConcurrentHashMap<String, Object> inputlist__OMP_ParallelRegion_0 = new ConcurrentHashMap<String,Object>();
        ConcurrentHashMap<String, Object> outputlist__OMP_ParallelRegion_0 = new ConcurrentHashMap<String,Object>();
        inputlist__OMP_ParallelRegion_0.put("result",result);
        inputlist__OMP_ParallelRegion_0.put("array",array);
        inputlist__OMP_ParallelRegion_0.put("n",n);
        inputlist__OMP_ParallelRegion_0.put("completeNumber",completeNumber);
        inputlist__OMP_ParallelRegion_0.put("service",service);
        _OMP_ParallelRegion_0 _OMP_ParallelRegion_0_in = new _OMP_ParallelRegion_0(_threadNum__OMP_ParallelRegion_0,icv__OMP_ParallelRegion_0,inputlist__OMP_ParallelRegion_0,outputlist__OMP_ParallelRegion_0);
        _OMP_ParallelRegion_0_in.runParallelCode();
        result = (List<MonitorInfoBean>)outputlist__OMP_ParallelRegion_0.get("result");
        array = (int[])outputlist__OMP_ParallelRegion_0.get("array");
        n = (Integer)outputlist__OMP_ParallelRegion_0.get("n");
        completeNumber = (Integer)outputlist__OMP_ParallelRegion_0.get("completeNumber");
        service = (MonitorInfo)outputlist__OMP_ParallelRegion_0.get("service");
        PjRuntime.recoverParentICV(icv_previous__OMP_ParallelRegion_0);
        RuntimeException OMP_ee_0 = (RuntimeException) _OMP_ParallelRegion_0_in.OMP_CurrentParallelRegionExceptionSlot.get();
        if (OMP_ee_0 != null) {throw OMP_ee_0;}
        /*OpenMP Parallel region (#0) -- END */

        service = new MonitorInfo();
        result.add(service.getMonitorInfoBean());
        return result;
    }
    }
        class _OMP_ParallelRegion_0{
                private int OMP_threadNumber = 1;
                private InternalControlVariables icv;
                private ConcurrentHashMap<String, Object> OMP_inputList = new ConcurrentHashMap<String, Object>();
                private ConcurrentHashMap<String, Object> OMP_outputList = new ConcurrentHashMap<String, Object>();
                private ReentrantLock OMP_lock;
                private ParIterator<?> OMP__ParIteratorCreator;
                public AtomicReference<Throwable> OMP_CurrentParallelRegionExceptionSlot = new AtomicReference<Throwable>(null);

                //#BEGIN shared variables defined here
                List<MonitorInfoBean> result = null;
                int[] array = null;
                MonitorInfo service = null;
                int n = 0;
                int completeNumber = 0;
                //#END shared variables defined here
                public _OMP_ParallelRegion_0(int thread_num, InternalControlVariables icv, ConcurrentHashMap<String, Object> inputlist, ConcurrentHashMap<String, Object> outputlist) {
                    this.icv = icv;
                    if ((false == Pyjama.omp_get_nested()) && (Pyjama.omp_get_level() > 0)) {
                        this.OMP_threadNumber = 1;
                    }else {
                        this.OMP_threadNumber = thread_num;
                    }
                    this.OMP_inputList = inputlist;
                    this.OMP_outputList = outputlist;
                    icv.currentParallelRegionThreadNumber = this.OMP_threadNumber;
                    icv.OMP_CurrentParallelRegionBarrier = new PjCyclicBarrier(this.OMP_threadNumber);
                    //#BEGIN shared variables initialised here
                    result = (List<MonitorInfoBean>)OMP_inputList.get("result");
                    array = (int[])OMP_inputList.get("array");
                    service = (MonitorInfo)OMP_inputList.get("service");
                    n = (Integer)OMP_inputList.get("n");
                    completeNumber = (Integer)OMP_inputList.get("completeNumber");
                    //#END shared variables initialised here
                }

                private void updateOutputListForSharedVars() {
                    //BEGIN update outputlist
                    OMP_outputList.put("result",result);
                    OMP_outputList.put("array",array);
                    OMP_outputList.put("n",n);
                    OMP_outputList.put("completeNumber",completeNumber);
                    OMP_outputList.put("service",service);
                    //END update outputlist
                }
                class MyCallable implements Callable<ConcurrentHashMap<String,Object>> {
                    private int alias_id;
                    private ConcurrentHashMap<String, Object> OMP_inputList;
                    private ConcurrentHashMap<String, Object> OMP_outputList;
                    //#BEGIN private/firstprivate reduction variables defined here
                    //#END private/firstprivate reduction variables  defined here
                    MyCallable(int id, ConcurrentHashMap<String,Object> inputlist, ConcurrentHashMap<String,Object> outputlist){
                        this.alias_id = id;
                        this.OMP_inputList = inputlist;
                        this.OMP_outputList = outputlist;
                        //#BEGIN firstprivate reduction variables initialised here
                        //#END firstprivate reduction variables initialised here
                    }

                    @Override
                    public ConcurrentHashMap<String,Object> call() {
                        try {
                            /****User Code BEGIN***/
                            {
                                while (completeNumber < n) {
                                    array[completeNumber] = completeNumber;
                                    completeNumber++;
                                    try {
                                        service = new MonitorInfo();
                                        double complete = (double) completeNumber / n;
                                        if (complete == 0.1) result.add(service.getMonitorInfoBean());
                                        if (complete == 0.2) result.add(service.getMonitorInfoBean());
                                        if (complete == 0.3) result.add(service.getMonitorInfoBean());
                                        if (complete == 0.4) result.add(service.getMonitorInfoBean());
                                        if (complete == 0.5) result.add(service.getMonitorInfoBean());
                                        if (complete == 0.6) result.add(service.getMonitorInfoBean());
                                        if (complete == 0.7) result.add(service.getMonitorInfoBean());
                                        if (complete == 0.8) result.add(service.getMonitorInfoBean());
                                        if (complete == 0.9) result.add(service.getMonitorInfoBean());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            /****User Code END***/
                            //BEGIN reduction procedure
                            //END reduction procedure
                            PjRuntime.setBarrier();
                        } catch (OmpParallelRegionLocalCancellationException e) {
                         	PjRuntime.decreaseBarrierCount();
                        } catch (Exception e) {
                            PjRuntime.decreaseBarrierCount();
                        	PjExecutor.cancelCurrentThreadGroup();
                        OMP_CurrentParallelRegionExceptionSlot.compareAndSet(null, e);
                    }
                    if (0 == this.alias_id) {
                        updateOutputListForSharedVars();
                    }
                    return null;
                }
            }
            public void runParallelCode() {
                for (int i = 1; i <= this.OMP_threadNumber-1; i++) {
                    Callable<ConcurrentHashMap<String,Object>> slaveThread = new MyCallable(i, OMP_inputList, OMP_outputList);
                    PjRuntime.submit(i, slaveThread, icv);
                }
                Callable<ConcurrentHashMap<String,Object>> masterThread = new MyCallable(0, OMP_inputList, OMP_outputList);
                PjRuntime.getCurrentThreadICV().currentThreadAliasID = 0;
                try {
                    masterThread.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }



}
