package PyjamaCode.TestingClauses.Schedule;

import java.util.ArrayList;
import java.util.List;
import Utility.MemoryAndCPUHelper.MonitorInfo;
import Domain.Bean.*;
import pj.Pyjama;

import pj.pr.*;
import pj.PjRuntime;
import pj.Pyjama;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.SwingUtilities;
import java.lang.reflect.InvocationTargetException;

public class schedule_Monitor {

    public List<MonitorInfoBean> parallel_for_static(int threadNumber, int n) throws Exception {{
        Pyjama.omp_set_num_threads(threadNumber);
        int[] completeNumber = new int[] { 0 };
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
        inputlist__OMP_ParallelRegion_0.put("completeNumber",completeNumber);
        inputlist__OMP_ParallelRegion_0.put("n",n);
        inputlist__OMP_ParallelRegion_0.put("array",array);
        inputlist__OMP_ParallelRegion_0.put("service",service);
        _OMP_ParallelRegion_0 _OMP_ParallelRegion_0_in = new _OMP_ParallelRegion_0(_threadNum__OMP_ParallelRegion_0,icv__OMP_ParallelRegion_0,inputlist__OMP_ParallelRegion_0,outputlist__OMP_ParallelRegion_0);
        _OMP_ParallelRegion_0_in.runParallelCode();
        result = (List)outputlist__OMP_ParallelRegion_0.get("result");
        completeNumber = (int[])outputlist__OMP_ParallelRegion_0.get("completeNumber");
        n = (Integer)outputlist__OMP_ParallelRegion_0.get("n");
        array = (int[])outputlist__OMP_ParallelRegion_0.get("array");
        service = (MonitorInfo)outputlist__OMP_ParallelRegion_0.get("service");
        PjRuntime.recoverParentICV(icv_previous__OMP_ParallelRegion_0);
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
        private CyclicBarrier OMP_barrier;
        private ReentrantLock OMP_lock;

        //#BEGIN shared variables defined here
        List result = null;
        MonitorInfo service = null;
        int n = 0;
        int[] completeNumber = null;
        int[] array = null;
        //#END shared variables defined here
        public _OMP_ParallelRegion_0(int thread_num, InternalControlVariables icv, ConcurrentHashMap<String, Object> inputlist, ConcurrentHashMap<String, Object> outputlist) {
            this.icv = icv;
            if ((false == Pyjama.omp_get_nested()) && (Pyjama.omp_get_level() > 0)) {
                this.OMP_threadNumber = 1;
            }else {
                this.OMP_threadNumber = thread_num;
            }
            icv.currentParallelRegionThreadNumber = this.OMP_threadNumber;
            this.OMP_inputList = inputlist;
            this.OMP_outputList = outputlist;
            this.OMP_barrier = new CyclicBarrier(this.OMP_threadNumber);
            icv.OMP_CurrentParallelRegionBarrier = new CyclicBarrier(this.OMP_threadNumber);
            icv.OMP_orderCursor = new AtomicInteger(0);
            //#BEGIN shared variables initialised here
            result = (List)OMP_inputList.get("result");
            service = (MonitorInfo)OMP_inputList.get("service");
            n = (Integer)OMP_inputList.get("n");
            completeNumber = (int[])OMP_inputList.get("completeNumber");
            array = (int[])OMP_inputList.get("array");
            //#END shared variables initialised here
        }

        private void updateOutputListForSharedVars() {
            //BEGIN update outputlist
            OMP_outputList.put("result",result);
            OMP_outputList.put("completeNumber",completeNumber);
            OMP_outputList.put("n",n);
            OMP_outputList.put("array",array);
            OMP_outputList.put("service",service);
            //END update outputlist
        }
        class MyCallable implements Callable<ConcurrentHashMap<String,Object>> {
            private int alias_id;
            private ConcurrentHashMap<String, Object> OMP_inputList;
            private ConcurrentHashMap<String, Object> OMP_outputList;
            //#BEGIN private/firstprivate reduction variables defined here
            //#END private/firstprivate reduction variables  defined here
            void setBarrier() {
                try {OMP_barrier.await();}
                catch (InterruptedException e) {e.printStackTrace();}
                catch (BrokenBarrierException e) {e.printStackTrace();}
            }
            MyCallable(int id, ConcurrentHashMap<String,Object> inputlist, ConcurrentHashMap<String,Object> outputlist){
                this.alias_id = id;
                this.OMP_inputList = inputlist;
                this.OMP_outputList = outputlist;
                //#BEGIN firstprivate reduction variables initialised here
                //#END firstprivate reduction variables initialised here
            }

            @Override
            public ConcurrentHashMap<String,Object> call() {
                InternalControlVariables currentThreadICV = new InternalControlVariables(icv);
                currentThreadICV.currentThreadAliasID = this.alias_id;
                PjRuntime.setCurrentThreadICV(currentThreadICV);

                
                /****User Code BEGIN***/
                /*OpenMP Work Share region (#0) -- START */
                
                {//#BEGIN firstprivate lastprivate reduction variables defined and initialized here
                    //#set implicit barrier here, otherwise unexpected initial value happens
                    PjRuntime.setBarrier();
                    //#END firstprivate lastprivate reduction variables defined and initialized here
                    int i=0;
                    int OMP_iterator = 0;
                    int OMP_end = (int)((n)-(0))/(1);
                    if (((n)-(0))%(1) == 0) {
                        OMP_end = OMP_end - 1;
                    }
                    int __omp_loop_thread_num = Pyjama.omp_get_thread_num();
                    int __omp_loop_num_threads = Pyjama.omp_get_num_threads();
                    for (OMP_iterator=__omp_loop_thread_num*1; OMP_iterator<=OMP_end && 1>0; OMP_iterator=OMP_iterator+__omp_loop_num_threads*1) {
                        for (int OMP_local_iterator = OMP_iterator; OMP_local_iterator<OMP_iterator+1 && OMP_local_iterator<=OMP_end; OMP_local_iterator++){
                            i = 0 + OMP_local_iterator * (1);
                            {
                                array[i] = i;
                                PjRuntime.OMP_lock.lock();
                                try {
                                    {
                                        completeNumber[0] = completeNumber[0] + 1;
                                    }} finally {
                                PjRuntime.OMP_lock.unlock();
                            }

                            try {
                                service = new MonitorInfo();
                                double complete = (double) completeNumber[0] / n;
                                if (complete == 0.1) result.add(service.getMonitorInfoBean());
                                if (complete == 0.2) result.add(service.getMonitorInfoBean());
                                if (complete == 0.3) result.add(service.getMonitorInfoBean());
                                if (complete == 0.4) result.add(service.getMonitorInfoBean());
                                if (complete == 0.5) result.add(service.getMonitorInfoBean());
                                if (complete == 0.6) result.add(service.getMonitorInfoBean());
                                if (complete == 0.7) result.add(service.getMonitorInfoBean());
                                if (complete == 0.8) result.add(service.getMonitorInfoBean());
                                if (complete == 0.9) result.add(service.getMonitorInfoBean());
                                if (complete == 1) result.add(service.getMonitorInfoBean());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }if (OMP_end == OMP_local_iterator) {
                            //BEGIN lastprivate variables value set
                            //END lastprivate variables value set
                        }
                    }
                }
                //BEGIN  reduction
                PjRuntime.reductionLockForWorksharing.lock();
                PjRuntime.reductionLockForWorksharing.unlock();//END reduction
                PjRuntime.setBarrier();
            }

                PjRuntime.setBarrier();
                /*OpenMP Work Share region (#0) -- END */

                /****User Code END***/
                //BEGIN reduction procedure
                //END reduction procedure
                setBarrier();
                if (0 == this.alias_id) {
                    updateOutputListForSharedVars();
                }
                return null;
            }
        }
        public void runParallelCode() {
            for (int i = 1; i <= this.OMP_threadNumber-1; i++) {
                Callable<ConcurrentHashMap<String,Object>> slaveThread = new MyCallable(i, OMP_inputList, OMP_outputList);
                PjRuntime.submit(slaveThread);
            }
            Callable<ConcurrentHashMap<String,Object>> masterThread = new MyCallable(0, OMP_inputList, OMP_outputList);
            try {
                masterThread.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    public List<MonitorInfoBean> parallel_for_dynamic(int threadNumber, int n) throws Exception {{
        Pyjama.omp_set_num_threads(threadNumber);
        int[] completeNumber = new int[] { 0 };
        int[] array = new int[n];
        MonitorInfo service = new MonitorInfo();
        List<MonitorInfoBean> result = new ArrayList<MonitorInfoBean>();
        /*OpenMP Parallel region (#1) -- START */
        InternalControlVariables icv_previous__OMP_ParallelRegion_1 = PjRuntime.getCurrentThreadICV();
        InternalControlVariables icv__OMP_ParallelRegion_1 = PjRuntime.inheritICV(icv_previous__OMP_ParallelRegion_1);
        int _threadNum__OMP_ParallelRegion_1 = icv__OMP_ParallelRegion_1.nthreads_var.get(icv__OMP_ParallelRegion_1.levels_var);
        ConcurrentHashMap<String, Object> inputlist__OMP_ParallelRegion_1 = new ConcurrentHashMap<String,Object>();
        ConcurrentHashMap<String, Object> outputlist__OMP_ParallelRegion_1 = new ConcurrentHashMap<String,Object>();
        inputlist__OMP_ParallelRegion_1.put("completeNumber",completeNumber);
        inputlist__OMP_ParallelRegion_1.put("service",service);
        inputlist__OMP_ParallelRegion_1.put("result",result);
        inputlist__OMP_ParallelRegion_1.put("n",n);
        inputlist__OMP_ParallelRegion_1.put("array",array);
        _OMP_ParallelRegion_1 _OMP_ParallelRegion_1_in = new _OMP_ParallelRegion_1(_threadNum__OMP_ParallelRegion_1,icv__OMP_ParallelRegion_1,inputlist__OMP_ParallelRegion_1,outputlist__OMP_ParallelRegion_1);
        _OMP_ParallelRegion_1_in.runParallelCode();
        completeNumber = (int[])outputlist__OMP_ParallelRegion_1.get("completeNumber");
        service = (MonitorInfo)outputlist__OMP_ParallelRegion_1.get("service");
        result = (List)outputlist__OMP_ParallelRegion_1.get("result");
        n = (Integer)outputlist__OMP_ParallelRegion_1.get("n");
        array = (int[])outputlist__OMP_ParallelRegion_1.get("array");
        PjRuntime.recoverParentICV(icv_previous__OMP_ParallelRegion_1);
        /*OpenMP Parallel region (#1) -- END */

        service = new MonitorInfo();
        result.add(service.getMonitorInfoBean());
        return result;
    }
    }
class _OMP_ParallelRegion_1{
        private int OMP_threadNumber = 1;
        private InternalControlVariables icv;
        private ConcurrentHashMap<String, Object> OMP_inputList = new ConcurrentHashMap<String, Object>();
        private ConcurrentHashMap<String, Object> OMP_outputList = new ConcurrentHashMap<String, Object>();
        private CyclicBarrier OMP_barrier;
        private ReentrantLock OMP_lock;

        //#BEGIN shared variables defined here
        List result = null;
        int n = 0;
        MonitorInfo service = null;
        int[] completeNumber = null;
        int[] array = null;
        //#END shared variables defined here
        public _OMP_ParallelRegion_1(int thread_num, InternalControlVariables icv, ConcurrentHashMap<String, Object> inputlist, ConcurrentHashMap<String, Object> outputlist) {
            this.icv = icv;
            if ((false == Pyjama.omp_get_nested()) && (Pyjama.omp_get_level() > 0)) {
                this.OMP_threadNumber = 1;
            }else {
                this.OMP_threadNumber = thread_num;
            }
            icv.currentParallelRegionThreadNumber = this.OMP_threadNumber;
            this.OMP_inputList = inputlist;
            this.OMP_outputList = outputlist;
            this.OMP_barrier = new CyclicBarrier(this.OMP_threadNumber);
            icv.OMP_CurrentParallelRegionBarrier = new CyclicBarrier(this.OMP_threadNumber);
            icv.OMP_orderCursor = new AtomicInteger(0);
            //#BEGIN shared variables initialised here
            result = (List)OMP_inputList.get("result");
            n = (Integer)OMP_inputList.get("n");
            service = (MonitorInfo)OMP_inputList.get("service");
            completeNumber = (int[])OMP_inputList.get("completeNumber");
            array = (int[])OMP_inputList.get("array");
            //#END shared variables initialised here
        }

        private void updateOutputListForSharedVars() {
            //BEGIN update outputlist
            OMP_outputList.put("completeNumber",completeNumber);
            OMP_outputList.put("service",service);
            OMP_outputList.put("result",result);
            OMP_outputList.put("n",n);
            OMP_outputList.put("array",array);
            //END update outputlist
        }
        class MyCallable implements Callable<ConcurrentHashMap<String,Object>> {
            private int alias_id;
            private ConcurrentHashMap<String, Object> OMP_inputList;
            private ConcurrentHashMap<String, Object> OMP_outputList;
            //#BEGIN private/firstprivate reduction variables defined here
            //#END private/firstprivate reduction variables  defined here
            void setBarrier() {
                try {OMP_barrier.await();}
                catch (InterruptedException e) {e.printStackTrace();}
                catch (BrokenBarrierException e) {e.printStackTrace();}
            }
            MyCallable(int id, ConcurrentHashMap<String,Object> inputlist, ConcurrentHashMap<String,Object> outputlist){
                this.alias_id = id;
                this.OMP_inputList = inputlist;
                this.OMP_outputList = outputlist;
                //#BEGIN firstprivate reduction variables initialised here
                //#END firstprivate reduction variables initialised here
            }

            @Override
            public ConcurrentHashMap<String,Object> call() {
                InternalControlVariables currentThreadICV = new InternalControlVariables(icv);
                currentThreadICV.currentThreadAliasID = this.alias_id;
                PjRuntime.setCurrentThreadICV(currentThreadICV);

                
                /****User Code BEGIN***/
                /*OpenMP Work Share region (#1) -- START */
                
                {//#BEGIN firstprivate lastprivate reduction variables defined and initialized here
                    //#set implicit barrier here, otherwise unexpected initial value happens
                    PjRuntime.setBarrier();
                    //#END firstprivate lastprivate reduction variables defined and initialized here
                    int i=0;
                    int OMP_iterator = 0;
                    int OMP_end = (int)((n)-(0))/(1);
                    if (((n)-(0))%(1) == 0) {
                        OMP_end = OMP_end - 1;
                    }
                    if (0 == Pyjama.omp_get_thread_num()) {
                        PjRuntime.get_OMP_loopCursor().getAndSet(0);}
                    PjRuntime.setBarrier();
                    while ((OMP_iterator = PjRuntime.get_OMP_loopCursor().getAndAdd(1)) <= OMP_end) {
                        for (int OMP_local_iterator = OMP_iterator; OMP_local_iterator<OMP_iterator+1 && OMP_local_iterator<=OMP_end; OMP_local_iterator++){
                            i = 0 + OMP_local_iterator * (1);
                            {
                                array[i] = i;
                                PjRuntime.OMP_lock.lock();
                                try {
                                    {
                                        completeNumber[0] = completeNumber[0] + 1;
                                    }} finally {
                                PjRuntime.OMP_lock.unlock();
                            }

                            try {
                                service = new MonitorInfo();
                                double complete = (double) completeNumber[0] / n;
                                if (complete == 0.1) result.add(service.getMonitorInfoBean());
                                if (complete == 0.2) result.add(service.getMonitorInfoBean());
                                if (complete == 0.3) result.add(service.getMonitorInfoBean());
                                if (complete == 0.4) result.add(service.getMonitorInfoBean());
                                if (complete == 0.5) result.add(service.getMonitorInfoBean());
                                if (complete == 0.6) result.add(service.getMonitorInfoBean());
                                if (complete == 0.7) result.add(service.getMonitorInfoBean());
                                if (complete == 0.8) result.add(service.getMonitorInfoBean());
                                if (complete == 0.9) result.add(service.getMonitorInfoBean());
                                if (complete == 1) result.add(service.getMonitorInfoBean());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }if (OMP_end == OMP_local_iterator) {
                            //BEGIN lastprivate variables value set
                            //END lastprivate variables value set
                        }

                    }
                }
                //BEGIN  reduction
                PjRuntime.reductionLockForWorksharing.lock();
                PjRuntime.reductionLockForWorksharing.unlock();//END reduction
                PjRuntime.setBarrier();
            }

                PjRuntime.setBarrier();
                /*OpenMP Work Share region (#1) -- END */

                /****User Code END***/
                //BEGIN reduction procedure
                //END reduction procedure
                setBarrier();
                if (0 == this.alias_id) {
                    updateOutputListForSharedVars();
                }
                return null;
            }
        }
        public void runParallelCode() {
            for (int i = 1; i <= this.OMP_threadNumber-1; i++) {
                Callable<ConcurrentHashMap<String,Object>> slaveThread = new MyCallable(i, OMP_inputList, OMP_outputList);
                PjRuntime.submit(slaveThread);
            }
            Callable<ConcurrentHashMap<String,Object>> masterThread = new MyCallable(0, OMP_inputList, OMP_outputList);
            try {
                masterThread.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    public List<MonitorInfoBean> parallel_for_guided(int threadNumber, int n) throws Exception {{
        Pyjama.omp_set_num_threads(threadNumber);
        int[] completeNumber = new int[] { 0 };
        int[] array = new int[n];
        MonitorInfo service = new MonitorInfo();
        List<MonitorInfoBean> result = new ArrayList<MonitorInfoBean>();
        /*OpenMP Parallel region (#2) -- START */
        InternalControlVariables icv_previous__OMP_ParallelRegion_2 = PjRuntime.getCurrentThreadICV();
        InternalControlVariables icv__OMP_ParallelRegion_2 = PjRuntime.inheritICV(icv_previous__OMP_ParallelRegion_2);
        int _threadNum__OMP_ParallelRegion_2 = icv__OMP_ParallelRegion_2.nthreads_var.get(icv__OMP_ParallelRegion_2.levels_var);
        ConcurrentHashMap<String, Object> inputlist__OMP_ParallelRegion_2 = new ConcurrentHashMap<String,Object>();
        ConcurrentHashMap<String, Object> outputlist__OMP_ParallelRegion_2 = new ConcurrentHashMap<String,Object>();
        inputlist__OMP_ParallelRegion_2.put("n",n);
        inputlist__OMP_ParallelRegion_2.put("completeNumber",completeNumber);
        inputlist__OMP_ParallelRegion_2.put("service",service);
        inputlist__OMP_ParallelRegion_2.put("array",array);
        inputlist__OMP_ParallelRegion_2.put("result",result);
        _OMP_ParallelRegion_2 _OMP_ParallelRegion_2_in = new _OMP_ParallelRegion_2(_threadNum__OMP_ParallelRegion_2,icv__OMP_ParallelRegion_2,inputlist__OMP_ParallelRegion_2,outputlist__OMP_ParallelRegion_2);
        _OMP_ParallelRegion_2_in.runParallelCode();
        n = (Integer)outputlist__OMP_ParallelRegion_2.get("n");
        completeNumber = (int[])outputlist__OMP_ParallelRegion_2.get("completeNumber");
        service = (MonitorInfo)outputlist__OMP_ParallelRegion_2.get("service");
        array = (int[])outputlist__OMP_ParallelRegion_2.get("array");
        result = (List)outputlist__OMP_ParallelRegion_2.get("result");
        PjRuntime.recoverParentICV(icv_previous__OMP_ParallelRegion_2);
        /*OpenMP Parallel region (#2) -- END */

        service = new MonitorInfo();
        result.add(service.getMonitorInfoBean());
        return result;
    }
    }
class _OMP_ParallelRegion_2{
        private int OMP_threadNumber = 1;
        private InternalControlVariables icv;
        private ConcurrentHashMap<String, Object> OMP_inputList = new ConcurrentHashMap<String, Object>();
        private ConcurrentHashMap<String, Object> OMP_outputList = new ConcurrentHashMap<String, Object>();
        private CyclicBarrier OMP_barrier;
        private ReentrantLock OMP_lock;

        //#BEGIN shared variables defined here
        List result = null;
        MonitorInfo service = null;
        int n = 0;
        int[] completeNumber = null;
        int[] array = null;
        //#END shared variables defined here
        public _OMP_ParallelRegion_2(int thread_num, InternalControlVariables icv, ConcurrentHashMap<String, Object> inputlist, ConcurrentHashMap<String, Object> outputlist) {
            this.icv = icv;
            if ((false == Pyjama.omp_get_nested()) && (Pyjama.omp_get_level() > 0)) {
                this.OMP_threadNumber = 1;
            }else {
                this.OMP_threadNumber = thread_num;
            }
            icv.currentParallelRegionThreadNumber = this.OMP_threadNumber;
            this.OMP_inputList = inputlist;
            this.OMP_outputList = outputlist;
            this.OMP_barrier = new CyclicBarrier(this.OMP_threadNumber);
            icv.OMP_CurrentParallelRegionBarrier = new CyclicBarrier(this.OMP_threadNumber);
            icv.OMP_orderCursor = new AtomicInteger(0);
            //#BEGIN shared variables initialised here
            result = (List)OMP_inputList.get("result");
            service = (MonitorInfo)OMP_inputList.get("service");
            n = (Integer)OMP_inputList.get("n");
            completeNumber = (int[])OMP_inputList.get("completeNumber");
            array = (int[])OMP_inputList.get("array");
            //#END shared variables initialised here
        }

        private void updateOutputListForSharedVars() {
            //BEGIN update outputlist
            OMP_outputList.put("n",n);
            OMP_outputList.put("completeNumber",completeNumber);
            OMP_outputList.put("service",service);
            OMP_outputList.put("array",array);
            OMP_outputList.put("result",result);
            //END update outputlist
        }
        class MyCallable implements Callable<ConcurrentHashMap<String,Object>> {
            private int alias_id;
            private ConcurrentHashMap<String, Object> OMP_inputList;
            private ConcurrentHashMap<String, Object> OMP_outputList;
            //#BEGIN private/firstprivate reduction variables defined here
            //#END private/firstprivate reduction variables  defined here
            void setBarrier() {
                try {OMP_barrier.await();}
                catch (InterruptedException e) {e.printStackTrace();}
                catch (BrokenBarrierException e) {e.printStackTrace();}
            }
            MyCallable(int id, ConcurrentHashMap<String,Object> inputlist, ConcurrentHashMap<String,Object> outputlist){
                this.alias_id = id;
                this.OMP_inputList = inputlist;
                this.OMP_outputList = outputlist;
                //#BEGIN firstprivate reduction variables initialised here
                //#END firstprivate reduction variables initialised here
            }

            @Override
            public ConcurrentHashMap<String,Object> call() {
                InternalControlVariables currentThreadICV = new InternalControlVariables(icv);
                currentThreadICV.currentThreadAliasID = this.alias_id;
                PjRuntime.setCurrentThreadICV(currentThreadICV);

                
                /****User Code BEGIN***/
                /*OpenMP Work Share region (#2) -- START */
                
                {//#BEGIN firstprivate lastprivate reduction variables defined and initialized here
                    //#set implicit barrier here, otherwise unexpected initial value happens
                    PjRuntime.setBarrier();
                    //#END firstprivate lastprivate reduction variables defined and initialized here
                    int i=0;
                    int OMP_iterator = 0;
                    int OMP_end = (int)((n)-(0))/(1);
                    if (((n)-(0))%(1) == 0) {
                        OMP_end = OMP_end - 1;
                    }
                    int OMP_chunkSize = 1;
                    if (0 == Pyjama.omp_get_thread_num()) {PjRuntime.get_OMP_loopCursor().getAndSet(0);}
                    PjRuntime.setBarrier();
                    while ((OMP_iterator = PjRuntime.get_OMP_loopCursor().getAndAdd(OMP_chunkSize)) <= OMP_end) {
                        for (int OMP_local_iterator = OMP_iterator; OMP_local_iterator<OMP_iterator+OMP_chunkSize && OMP_local_iterator<=OMP_end; OMP_local_iterator++){
                            i = 0 + OMP_local_iterator * (1);
                            {
                                array[i] = i;
                                PjRuntime.OMP_lock.lock();
                                try {
                                    {
                                        completeNumber[0] = completeNumber[0] + 1;
                                    }} finally {
                                PjRuntime.OMP_lock.unlock();
                            }

                            try {
                                service = new MonitorInfo();
                                double complete = (double) completeNumber[0] / n;
                                if (complete == 0.1) result.add(service.getMonitorInfoBean());
                                if (complete == 0.2) result.add(service.getMonitorInfoBean());
                                if (complete == 0.3) result.add(service.getMonitorInfoBean());
                                if (complete == 0.4) result.add(service.getMonitorInfoBean());
                                if (complete == 0.5) result.add(service.getMonitorInfoBean());
                                if (complete == 0.6) result.add(service.getMonitorInfoBean());
                                if (complete == 0.7) result.add(service.getMonitorInfoBean());
                                if (complete == 0.8) result.add(service.getMonitorInfoBean());
                                if (complete == 0.9) result.add(service.getMonitorInfoBean());
                                if (complete == 1) result.add(service.getMonitorInfoBean());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }if (OMP_end == OMP_local_iterator) {
                            //BEGIN lastprivate variables value set
                            //END lastprivate variables value set
                        }

                    }
                    if(OMP_chunkSize>1)OMP_chunkSize--;
                }
                //BEGIN  reduction
                PjRuntime.reductionLockForWorksharing.lock();
                PjRuntime.reductionLockForWorksharing.unlock();//END reduction
                PjRuntime.setBarrier();
            }

                PjRuntime.setBarrier();
                /*OpenMP Work Share region (#2) -- END */

                /****User Code END***/
                //BEGIN reduction procedure
                //END reduction procedure
                setBarrier();
                if (0 == this.alias_id) {
                    updateOutputListForSharedVars();
                }
                return null;
            }
        }
        public void runParallelCode() {
            for (int i = 1; i <= this.OMP_threadNumber-1; i++) {
                Callable<ConcurrentHashMap<String,Object>> slaveThread = new MyCallable(i, OMP_inputList, OMP_outputList);
                PjRuntime.submit(slaveThread);
            }
            Callable<ConcurrentHashMap<String,Object>> masterThread = new MyCallable(0, OMP_inputList, OMP_outputList);
            try {
                masterThread.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
