import { element, by, ElementFinder } from 'protractor';

export class MusicaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-musica div table .btn-danger'));
  title = element.all(by.css('jhi-musica div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class MusicaUpdatePage {
  pageTitle = element(by.id('jhi-musica-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  trackInput = element(by.id('field_track'));

  playlistSelect = element(by.id('field_playlist'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setTrackInput(track: string): Promise<void> {
    await this.trackInput.sendKeys(track);
  }

  async getTrackInput(): Promise<string> {
    return await this.trackInput.getAttribute('value');
  }

  async playlistSelectLastOption(): Promise<void> {
    await this.playlistSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async playlistSelectOption(option: string): Promise<void> {
    await this.playlistSelect.sendKeys(option);
  }

  getPlaylistSelect(): ElementFinder {
    return this.playlistSelect;
  }

  async getPlaylistSelectedOption(): Promise<string> {
    return await this.playlistSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class MusicaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-musica-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-musica'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
