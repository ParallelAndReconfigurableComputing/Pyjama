package jUnitTestSuits.compileTests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import pj.PyjamaToJavaParser;
import pj.Version;
import Domain.SettingConstans;
import Domain.Bean.Data;
import Domain.Bean.Result;
import Utility.JSONDatabaseHelper.resultWriterHelper;
import Utility.JavaCompileHelper.JavaCompiler;

public class CopyPrivateTest {

	private static Data Data= new Data();
	
	@Rule
	public Timeout globalTimeout = new Timeout(SettingConstans.TIMEOUT);
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Data.setVersion(Version.compilerVersion);
		resultWriterHelper n = new resultWriterHelper();
		n.write(Data,"JsonResult","Copyprivate","CompileJunitResults",false);
	}
	
	@Test
	public void PositiveTest1() {		
		if(!positiveTest("PositiveTest1","copyprivate_positive_test1","test1","Copyprivate clause in single directive"))
			fail("Compile Failed");
	}
	
	@Test
	public void NegativeTest1() {		
		if(!negativeTest("NegativeTest1","copyprivate_negative_test1","Copyprivate clause in parallel directive"))
			fail("Compile Failed");
	}
	
	private boolean positiveTest(String name,String filename,String test,String remark){
		Result result = new Result();		
		result.setTestName(name);
		result.setCompilePJFilePath(SettingConstans.COMPILETARGETTESTCODEPATH+"/PyjamaCode/TestingClauses/Copyprivate/"+filename+".pj");
		result.setRemark(remark);
		result.setCompileResult("failed");
		result.setJunitResult("TimeOut");
		Data.addResult(result);
		
		try {
			PyjamaToJavaParser.parse(new File(SettingConstans.COMPILETARGETTESTCODEPATH+"/PyjamaCode/TestingClauses/Copyprivate/"+filename+".pj"));
			JavaCompiler builder = new JavaCompiler();
			List<String> error =builder.buildJava(SettingConstans.COMPILETARGETTESTCODEPATH+"/PyjamaCode/TestingClauses/Copyprivate/",test);
			if (error.size()>0){
				result.setCompileResult(error.toString());
				result.setJunitResult("notPass");
				Data.ReSetResult(result);
				return false;
			}
			else{
				result.setCompileResult("success");
				result.setJunitResult("Pass");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(e.getMessage()==null){
				result.setJunitResult("notPass");
			}else{
				result.setCompileResult(e.getMessage());
			
			if(e.getMessage().startsWith("Unsupported"))
				result.setJunitResult("Unsupported");
			else
				result.setJunitResult("notPass");
			}
			Data.ReSetResult(result);
			return false;
		}
		Data.ReSetResult(result);
		return true;
	}
	
	private boolean negativeTest(String name,String filename,String remark){
		Result result = new Result();
		result.setTestName(name);
		result.setCompilePJFilePath(SettingConstans.COMPILETARGETTESTCODEPATH+"/PyjamaCode/TestingClauses/Copyprivate/"+filename+".pj");
		result.setRemark(remark);
		result.setCompileResult("failed");
		result.setJunitResult("TimeOut");
		Data.addResult(result);
		
		try {
			PyjamaToJavaParser.parse(new File(SettingConstans.COMPILETARGETTESTCODEPATH+"/PyjamaCode/TestingClauses/Copyprivate/"+filename+".pj"));
		} catch (Exception e) {
			result.setCompileResult("Should failed");
			result.setJunitResult("Pass");
			Data.ReSetResult(result);
			e.printStackTrace();
			return true;
		}
		result.setCompileResult("Shouldn't success");
		result.setJunitResult("notPass");
		Data.ReSetResult(result);
		return false;
	}
}
