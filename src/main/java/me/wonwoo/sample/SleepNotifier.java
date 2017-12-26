package me.wonwoo.sample;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.FreeStyleProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SleepNotifier extends Notifier {

  private long time;

  @DataBoundConstructor
  public SleepNotifier(long time) {
    this.time = time;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  @Override
  public BuildStepMonitor getRequiredMonitorService() {
    return BuildStepMonitor.NONE;
  }

  @Override
  public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
    listener.getLogger().println("Sleeping for: " + time + " ms.");
    TimeUnit.MILLISECONDS.sleep(time);
    return true;
  }

  @Extension
  public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {

    @Override
    public boolean isApplicable(Class<? extends AbstractProject> aClass) {
      return aClass == FreeStyleProject.class;
    }

    @Override
    public String getDisplayName() {
      return "Sleep";
    }
  }
}
