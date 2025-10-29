import { Injectable } from '@angular/core';
import { AbstractControl } from '@angular/forms';

@Injectable({
  providedIn: 'root',
})
export class CustomValidator {
  public passwordFormat(control: AbstractControl) {
    let testFormat: boolean =
      /[0-9]/.test(control.value) &&
      /[a-z]/.test(control.value) &&
      /[A-Z]/.test(control.value) &&
      /[!@#$%^&*(),.?":{}|<>]/.test(control.value);
    return testFormat ? null : { pattern: true };
  }
}
