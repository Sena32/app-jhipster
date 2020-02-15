import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProdutoComponentsPage, ProdutoDeleteDialog, ProdutoUpdatePage } from './produto.page-object';

const expect = chai.expect;

describe('Produto e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let produtoComponentsPage: ProdutoComponentsPage;
  let produtoUpdatePage: ProdutoUpdatePage;
  let produtoDeleteDialog: ProdutoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Produtos', async () => {
    await navBarPage.goToEntity('produto');
    produtoComponentsPage = new ProdutoComponentsPage();
    await browser.wait(ec.visibilityOf(produtoComponentsPage.title), 5000);
    expect(await produtoComponentsPage.getTitle()).to.eq('jhipsterApp.produto.home.title');
    await browser.wait(ec.or(ec.visibilityOf(produtoComponentsPage.entities), ec.visibilityOf(produtoComponentsPage.noResult)), 1000);
  });

  it('should load create Produto page', async () => {
    await produtoComponentsPage.clickOnCreateButton();
    produtoUpdatePage = new ProdutoUpdatePage();
    expect(await produtoUpdatePage.getPageTitle()).to.eq('jhipsterApp.produto.home.createOrEditLabel');
    await produtoUpdatePage.cancel();
  });

  it('should create and save Produtos', async () => {
    const nbButtonsBeforeCreate = await produtoComponentsPage.countDeleteButtons();

    await produtoComponentsPage.clickOnCreateButton();

    await promise.all([produtoUpdatePage.setNameInput('name'), produtoUpdatePage.setPriceInput('5')]);

    expect(await produtoUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await produtoUpdatePage.getPriceInput()).to.eq('5', 'Expected price value to be equals to 5');

    await produtoUpdatePage.save();
    expect(await produtoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await produtoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Produto', async () => {
    const nbButtonsBeforeDelete = await produtoComponentsPage.countDeleteButtons();
    await produtoComponentsPage.clickOnLastDeleteButton();

    produtoDeleteDialog = new ProdutoDeleteDialog();
    expect(await produtoDeleteDialog.getDialogTitle()).to.eq('jhipsterApp.produto.delete.question');
    await produtoDeleteDialog.clickOnConfirmButton();

    expect(await produtoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
