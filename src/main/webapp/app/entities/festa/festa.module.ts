import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { FestaComponent } from './festa.component';
import { FestaDetailComponent } from './festa-detail.component';
import { festaRoute } from './festa.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(festaRoute)],
  declarations: [FestaComponent, FestaDetailComponent]
})
export class JhipsterFestaModule {}
