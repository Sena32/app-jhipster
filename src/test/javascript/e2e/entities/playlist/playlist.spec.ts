import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PlaylistComponentsPage, PlaylistDeleteDialog, PlaylistUpdatePage } from './playlist.page-object';

const expect = chai.expect;

describe('Playlist e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let playlistComponentsPage: PlaylistComponentsPage;
  let playlistUpdatePage: PlaylistUpdatePage;
  let playlistDeleteDialog: PlaylistDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Playlists', async () => {
    await navBarPage.goToEntity('playlist');
    playlistComponentsPage = new PlaylistComponentsPage();
    await browser.wait(ec.visibilityOf(playlistComponentsPage.title), 5000);
    expect(await playlistComponentsPage.getTitle()).to.eq('jhipsterApp.playlist.home.title');
    await browser.wait(ec.or(ec.visibilityOf(playlistComponentsPage.entities), ec.visibilityOf(playlistComponentsPage.noResult)), 1000);
  });

  it('should load create Playlist page', async () => {
    await playlistComponentsPage.clickOnCreateButton();
    playlistUpdatePage = new PlaylistUpdatePage();
    expect(await playlistUpdatePage.getPageTitle()).to.eq('jhipsterApp.playlist.home.createOrEditLabel');
    await playlistUpdatePage.cancel();
  });

  it('should create and save Playlists', async () => {
    const nbButtonsBeforeCreate = await playlistComponentsPage.countDeleteButtons();

    await playlistComponentsPage.clickOnCreateButton();

    await promise.all([
      playlistUpdatePage.setNameInput('name'),
      playlistUpdatePage.setStyleInput('style'),
      playlistUpdatePage.setDtCreateInput('01/01/2001' + protractor.Key.TAB + '02:30AM')
    ]);

    expect(await playlistUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await playlistUpdatePage.getStyleInput()).to.eq('style', 'Expected Style value to be equals to style');
    expect(await playlistUpdatePage.getDtCreateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dtCreate value to be equals to 2000-12-31'
    );

    await playlistUpdatePage.save();
    expect(await playlistUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await playlistComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Playlist', async () => {
    const nbButtonsBeforeDelete = await playlistComponentsPage.countDeleteButtons();
    await playlistComponentsPage.clickOnLastDeleteButton();

    playlistDeleteDialog = new PlaylistDeleteDialog();
    expect(await playlistDeleteDialog.getDialogTitle()).to.eq('jhipsterApp.playlist.delete.question');
    await playlistDeleteDialog.clickOnConfirmButton();

    expect(await playlistComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
