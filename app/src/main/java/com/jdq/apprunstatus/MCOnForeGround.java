package com.jdq.apprunstatus;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

public class MCOnForeGround {

	private static final String TAG = "MCOnForeGround";
	public static boolean onForeground = false;

	/**
	 * 程序是否在前台运行
	 * 
	 * @return
	 */
	public static boolean isAppOnForeground(Context context) {
		// Returns a list of application processes that are running on the
		// device

		ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = context.getApplicationContext().getPackageName();

		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		if (appProcesses == null)
			return false;

		for (RunningAppProcessInfo appProcess : appProcesses) {
			// The name of the process that this object is associated with.
			if (appProcess.processName.equals(packageName) && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断当前应用程序处于前台还是后台
	 * 
	 * @param context
	 * 
	 * @return
	 */
	public static boolean isApplicationProcess(final Context context) {

		ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = context.getApplicationContext().getPackageName();

		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();

		if (appProcesses != null && appProcesses.contains(packageName))
			return true;

		return false;
	}

	/**
	 * 判断当前应用程序处于前台还是后台
	 * 
	 * @param context
	 * 
	 * @return
	 */
	public static boolean isApplicationForeground(final Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (topActivity.getPackageName().equals(context.getPackageName())) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 获取系统中正在运行的任务信息（强调下，任务是多个activity的集合）
	 */
	public static void run(Context context) {
		String info = "";
		// 获取的正在运行的activity的最大数量（防止太多，系统承受不了啊）、
		int maxNum = 40;
		// ActivityManager的功能是为系统中所有运行着的Activity交互提供了接口，主要的接口围绕着运行中的进程信息，任务信息，服务信息等
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
		/**
		 * 获取正在运行的任务这里一定要注意，这里我们获取的时候，
		 * 你的任务或者其中的activity可能没结束，但是当你在后边使用的时候，很有可能已经被kill了哦。
		 * 意思很简单，系统返给你的正在运行的task，是暂态的，仅仅代表你调用该方法时系统中的状态， 至于后边是否发生了该变，系统概不负责、
		 */
		List<RunningTaskInfo> runningTasks = activityManager.getRunningTasks(maxNum);
		for (RunningTaskInfo taskInfo : runningTasks) {
			info += "一个任务信息开始：\n";
			info += "当前任务中正处于运行状态的activity数目:" + taskInfo.numRunning + "\n";
			info += "当前任务中的activity数目: " + taskInfo.numActivities + "\n";
			info += "启动当前任务的activity名称:" + taskInfo.baseActivity.getClassName() + "\n";
			info += "最上面任务的activity名称:" + taskInfo.topActivity + "\n";

			Log.i("MC", info.toString());

		}

	}

	/**
	 * 获取栈顶activity
	 * 
	 * @return
	 */
	public static String getTopActivity(Context context) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
		if (runningTaskInfos != null) {
			Log.i(TAG, "getTopActivity : " + (runningTaskInfos.get(0).topActivity).getClassName());
			return (runningTaskInfos.get(0).topActivity).getClassName();
		} else
			return null;
	}

	/**
	 * 用来判断服务是否运行.
	 * 
	 * @param context
	 * 
	 * @param className
	 *            判断的服务名字
	 * @return true 在运行 false 不在运行
	 */
	public static boolean isServiceRunning(Context mContext, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(30);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}
}
