import { Component } from '@angular/core';
import { NavBar } from '../../nav-bar/nav-bar';
import { MatIcon } from '@angular/material/icon';
import { MatIconButton } from '@angular/material/button';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-create',
  imports: [NavBar, MatIcon, MatIconButton, RouterLink],
  templateUrl: './create.html',
  styleUrl: './create.scss',
})
export class Create {}
