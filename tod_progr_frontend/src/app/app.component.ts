import { Component } from '@angular/core';
import { EventSettingsModel, DayService, WeekService, WorkWeekService, MonthService, AgendaService, DragAndDropService, ResizeService } from '@syncfusion/ej2-angular-schedule';

@Component({
  selector: 'app-root',
  providers: [DayService, WeekService, WorkWeekService, MonthService, AgendaService,DragAndDropService, ResizeService],
  // specifies the template string for the Schedule component
  template: `<ejs-schedule width='1250' height="850"
  [eventSettings]='eventSettings'></ejs-schedule>`
})
export class AppComponent {
  public data: object [] = [{
    id: 2,
    eventName: 'Meeting',
    startTime: new Date(2021, 2, 4, 3,3), //year, month-1, day , hours, minutes
    endTime: new Date(2021, 2, 4, 3, 3), //year, month-1, day , hours, minutes
    isAllDay: false
  }];

  public eventSettings: EventSettingsModel = {
    dataSource: this.data,
    fields: {
      id: 'id',
      subject: { name: 'eventName' },
      isAllDay: { name: 'isAllDay' },
      startTime: { name: 'startTime' },
      endTime: { name: 'endTime' },
    }
  };
}
export class AppModule { };