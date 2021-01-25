import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { FormControl, FormGroup } from '@angular/forms';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  @ViewChild('recaptcha', { static: true }) recaptchaElement: ElementRef;
  codeCaptcha: string;

  constructor(private router:Router) { }

  ngOnInit(): void {
    this.addRecaptchaScript();
  }


  addRecaptchaScript() {
    window['grecaptchaCallback'] = () => {
      this.renderReCaptcha();
    }
    (function (d, s, id, obj) {
      var js, fjs = d.getElementsByTagName(s)[0];
      if (d.getElementById(id)) { return; }
      js = d.createElement(s); js.id = id;
      js.src = "https://www.google.com/recaptcha/api.js?onload=grecaptchaCallback&render=explicit&hl=es";
      fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'recaptcha-jssdk', this));
  }


  renderReCaptcha() {
    window['grecaptcha'].render(this.recaptchaElement.nativeElement, {
      'sitekey' : environment.siteKey,
      'lang': 'es',
      'callback': (codeVerify) => {
        this.codeCaptcha = codeVerify;
      }
    });
  }

  login() {
    
    if(!this.codeCaptcha) {
      alert('Debe completar el captcha');
      return;
    }
    this.router.navigate(['home']);
  }

}
