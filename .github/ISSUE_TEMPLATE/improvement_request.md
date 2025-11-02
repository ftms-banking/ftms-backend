name: 🔧 Improvement Request
description: Suggest enhancements or optimizations to existing modules.
labels: [improvement, backlog]
title: "[IMPROVEMENT] <brief title>"
body:
  - type: markdown
    attributes:
      value: |
        ## Improvement Overview
        Provide a description of the enhancement area.
  - type: textarea
    id: current_state
    attributes:
      label: Current State
      description: Describe the current implementation or behavior.
  - type: textarea
    id: proposed_change
    attributes:
      label: Proposed Change
      description: What’s your proposed improvement or optimization?
  - type: textarea
    id: benefits
    attributes:
      label: Business / Technical Benefits
      description: Why should this improvement be prioritized?
  - type: dropdown
    id: complexity
    attributes:
      label: Estimated Complexity
      options:
        - Low
        - Medium
        - High
  - type: textarea
    id: dependencies
    attributes:
      label: Dependencies / Risks
      description: Note any impacted modules or potential risks.
