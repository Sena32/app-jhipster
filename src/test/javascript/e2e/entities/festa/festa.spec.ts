import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FestaComponentsPage } from './festa.page-object';

const expect = chai.expect;

describe('Festa e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let festaComponentsPage: FestaComponentsPage;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Festas', async () => {
    await navBarPage.goToEntity('festa');
    festaComponentsPage = new FestaComponentsPage();
    await browser.wait(ec.visibilityOf(festaComponentsPage.title), 5000);
    expect(await festaComponentsPage.getTitle()).to.eq('jhipsterApp.festa.home.title');
    await browser.wait(ec.or(ec.visibilityOf(festaComponentsPage.entities), ec.visibilityOf(festaComponentsPage.noResult)), 1000);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
