import { Component, ElementRef, Input, OnInit, OnChanges, SimpleChanges, ViewChild, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-code-editor',
  standalone: true,
  imports: [CommonModule, FormsModule, MatButtonModule, MatIconModule, MatSnackBarModule],
  template: `
    <div class="editor-wrapper">
      <div class="editor-container">
        <div class="editor-header">
          <span class="file-name">{{ fileName || 'Code Editor' }}</span>
          <div class="editor-actions">
            <button mat-icon-button (click)="resetCode()" title="Reset Code">
              <mat-icon>refresh</mat-icon>
            </button>
            <button mat-icon-button (click)="copyCode()" title="Copy Code">
              <mat-icon>content_copy</mat-icon>
            </button>
          </div>
        </div>
        <textarea
          #codeTextarea
          class="code-textarea"
          [(ngModel)]="currentCode"
          [placeholder]="placeholder"
          spellcheck="false"
          (input)="onCodeChange()"
        ></textarea>
        <div class="editor-footer">
          <span class="language-tag">{{ language }}</span>
          <span class="line-count">{{ getLineCount() }} lines</span>
        </div>
      </div>
    </div>
  `,
  styleUrls: ['./code-editor.component.scss']
})
export class CodeEditorComponent implements OnInit, OnChanges {
  @ViewChild('codeTextarea', { static: true }) codeTextarea!: ElementRef<HTMLTextAreaElement>;
  @Input() initialCode: string = ''; // Original template for reset
  @Input() code: string = ''; // Current code value
  @Input() language: string = 'java';
  @Input() fileName: string = '';
  @Input() placeholder: string = 'Enter your code here...';
  @Output() codeChanged = new EventEmitter<string>();

  currentCode: string = '';

  constructor(private snackBar: MatSnackBar) {}

  ngOnInit() {
    this.currentCode = this.code || this.initialCode;
    this.setupTextareaFeatures();
  }

  ngOnChanges(changes: SimpleChanges) {
    // Update currentCode when code input changes from parent
    if (changes['code'] && !changes['code'].firstChange) {
      this.currentCode = this.code;
    }
  }

  private setupTextareaFeatures() {
    const textarea = this.codeTextarea.nativeElement;
    
    // Add tab support
    textarea.addEventListener('keydown', (e) => {
      if (e.key === 'Tab') {
        e.preventDefault();
        const start = textarea.selectionStart;
        const end = textarea.selectionEnd;
        
        // Insert tab character
        textarea.value = textarea.value.substring(0, start) + '    ' + textarea.value.substring(end);
        textarea.selectionStart = textarea.selectionEnd = start + 4;
        
        // Update ngModel
        this.currentCode = textarea.value;
        this.onCodeChange();
      }
    });
  }

  onCodeChange() {
    this.codeChanged.emit(this.currentCode);
  }

  getValue(): string {
    return this.currentCode;
  }

  setValue(value: string): void {
    this.currentCode = value;
  }

  resetCode(): void {
    this.currentCode = this.initialCode;
    this.onCodeChange();
    this.snackBar.open('Code reset to template', 'Close', { duration: 2000 });
  }

  copyCode(): void {
    navigator.clipboard.writeText(this.currentCode).then(() => {
      this.snackBar.open('Code copied to clipboard', 'Close', { duration: 2000 });
    }).catch(err => {
      console.error('Failed to copy code:', err);
      this.snackBar.open('Failed to copy code', 'Close', { duration: 2000 });
    });
  }

  getLineCount(): number {
    return this.currentCode.split('\n').length;
  }
}