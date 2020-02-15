import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MusicaComponentsPage, MusicaDeleteDialog, MusicaUpdatePage } from './musica.page-object';

const expect = chai.expect;

describe('Musica e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let musicaComponentsPage: MusicaComponentsPage;
  let musicaUpdatePage: MusicaUpdatePage;
  let musicaDeleteDialog: MusicaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Musicas', async () => {
    await navBarPage.goToEntity('musica');
    musicaComponentsPage = new MusicaComponentsPage();
    await browser.wait(ec.visibilityOf(musicaComponentsPage.title), 5000);
    expect(await musicaComponentsPage.getTitle()).to.eq('jhipsterApp.musica.home.title');
    await browser.wait(ec.or(ec.visibilityOf(musicaComponentsPage.entities), ec.visibilityOf(musicaComponentsPage.noResult)), 1000);
  });

  it('should load create Musica page', async () => {
    await musicaComponentsPage.clickOnCreateButton();
    musicaUpdatePage = new MusicaUpdatePage();
    expect(await musicaUpdatePage.getPageTitle()).to.eq('jhipsterApp.musica.home.createOrEditLabel');
    await musicaUpdatePage.cancel();
  });

  it('should create and save Musicas', async () => {
    const nbButtonsBeforeCreate = await musicaComponentsPage.countDeleteButtons();

    await musicaComponentsPage.clickOnCreateButton();

    await promise.all([
      musicaUpdatePage.setNameInput('name'),
      musicaUpdatePage.setTrackInput('5'),
      musicaUpdatePage.playlistSelectLastOption()
    ]);

    expect(await musicaUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await musicaUpdatePage.getTrackInput()).to.eq('5', 'Expected track value to be equals to 5');

    await musicaUpdatePage.save();
    expect(await musicaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await musicaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Musica', async () => {
    const nbButtonsBeforeDelete = await musicaComponentsPage.countDeleteButtons();
    await musicaComponentsPage.clickOnLastDeleteButton();

    musicaDeleteDialog = new MusicaDeleteDialog();
    expect(await musicaDeleteDialog.getDialogTitle()).to.eq('jhipsterApp.musica.delete.question');
    await musicaDeleteDialog.clickOnConfirmButton();

    expect(await musicaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
