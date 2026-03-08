import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';

  languages = [
    { code: 'es', name: 'Español', flag: '🇪🇸', dir: 'ltr' },
    { code: 'en', name: 'English', flag: '🇺🇸', dir: 'ltr' },
    { code: 'fa', name: 'فارسی', flag: '🇮🇷', dir: 'rtl' },
    { code: 'he', name: 'עברית', flag: '🇮🇱', dir: 'rtl' }
  ];

  constructor(private translate: TranslateService) {
    this.translate.setDefaultLang('es');
    this.switchLanguage('es');
  }

  switchLanguage(langCode: string) {
    const lang = this.languages.find(l => l.code === langCode);
    if (lang) {
      this.translate.use(lang.code);
      document.documentElement.dir = lang.dir;
      document.documentElement.lang = lang.code;
    }
  }
}
