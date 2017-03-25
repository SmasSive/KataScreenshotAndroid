package com.karumi.screenshot;

import android.app.Activity;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import com.karumi.screenshot.di.MainComponent;
import com.karumi.screenshot.di.MainModule;
import com.karumi.screenshot.model.SuperHero;
import com.karumi.screenshot.model.SuperHeroesRepository;
import com.karumi.screenshot.ui.view.SuperHeroDetailActivity;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.when;

public class SuperHeroDetailActivityTest extends ScreenshotTest {

  @Rule public DaggerMockRule<MainComponent> daggerRule =
      new DaggerMockRule<>(MainComponent.class, new MainModule()).set(
          new DaggerMockRule.ComponentSetter<MainComponent>() {
            @Override public void setComponent(MainComponent component) {
              SuperHeroesApplication app =
                  (SuperHeroesApplication) InstrumentationRegistry.getInstrumentation()
                      .getTargetContext()
                      .getApplicationContext();
              app.setComponent(component);
            }
          });

  @Rule public ActivityTestRule<SuperHeroDetailActivity> activityRule =
      new ActivityTestRule<>(SuperHeroDetailActivity.class, true, false);

  @Mock SuperHeroesRepository repository;

  @Test public void showsAnySuperHero() throws Exception {
    SuperHero superHero = givenASuperHero();

    Activity activity = startActivity(superHero);

    compareScreenshot(activity);
  }

  @Test public void showsAnySuperHeroWithoutName() throws Exception {
    SuperHero superHero = givenASuperHeroWithoutName();

    Activity activity = startActivity(superHero);

    compareScreenshot(activity);
  }

  @Test public void showsAnySuperHeroWithoutDescription() throws Exception {
    SuperHero superHero = givenASuperHeroWithoutDescription();

    Activity activity = startActivity(superHero);

    compareScreenshot(activity);
  }

  @Test public void showsAnAvenger() throws Exception {
    SuperHero superHero = givenAnAvenger();

    Activity activity = startActivity(superHero);

    compareScreenshot(activity);
  }

  private SuperHero givenASuperHero() {
    String superHeroName = "Custom Hero";
    SuperHero superHero = new SuperHero(superHeroName,
        "http://www.myfreewallpapers.net/comics/wallpapers/captain-america-lives.jpg", false,
        "Custom Description");
    when(repository.getByName(superHeroName)).thenReturn(superHero);
    return superHero;
  }

  private SuperHero givenASuperHeroWithoutName() {
    String superHeroName = "";
    SuperHero superHero = new SuperHero(superHeroName,
        "http://www.myfreewallpapers.net/comics/wallpapers/captain-america-lives.jpg", false,
        "Custom Description");
    when(repository.getByName(superHeroName)).thenReturn(superHero);
    return superHero;
  }

  private SuperHero givenASuperHeroWithoutDescription() {
    String superHeroName = "Custom Hero";
    String superHeroDescription = "";
    SuperHero superHero = new SuperHero(superHeroName,
        "http://www.myfreewallpapers.net/comics/wallpapers/captain-america-lives.jpg", false,
        superHeroDescription);
    when(repository.getByName(superHeroName)).thenReturn(superHero);
    return superHero;
  }

  private SuperHero givenAnAvenger() {
    String superHeroName = "Custom Avenger Hero";
    SuperHero superHero = new SuperHero(superHeroName,
        "http://www.myfreewallpapers.net/comics/wallpapers/captain-america-lives.jpg", true,
        "Custom Avenger Description");
    when(repository.getByName(superHeroName)).thenReturn(superHero);
    return superHero;
  }

  private SuperHeroDetailActivity startActivity(SuperHero superHero) {
    Intent intent = new Intent();
    intent.putExtra("super_hero_name_key", superHero.getName());
    return activityRule.launchActivity(intent);
  }
}
