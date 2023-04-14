# Single Sign On

## Development

In order to start working on the project follow these steps:

- Go to **issues** tab and click the **list**

![screenshot1](https://i.imgur.com/C1i9G01.png)

- **Pick** the issue you want to work on or **create** it along with its description. *(Remember to add a title prefix indicating lab number)*

![screenshot2](https://i.imgur.com/uIjsaQD.png)

- From the dropdown button *(screenshot below)* choode `Create branch`, **not** `Create merge request and branch`

![screenshot3](https://i.imgur.com/qHKMuc5.png)

- Leave the name as it is. Now click the blue button below - `Create branch`
- Now you go to your computer terminal at the root of the project folder and type `git fetch origin` in order to retrieve all git changes from **remote** repository *(your newly created branch)*
- When done, type `git checkout <the_branch_name>` to work on the branch. If you have `oh-my-posh` properly set up, you should see the branch name update like on screenshot below. If you don't, you can type `git status` and the first line should indicate the name of the branch.
![screenshot4](https://i.imgur.com/XpmOrXO.png)
- After coding, changing etc, when you finish the issue, `git add <path>` all files you want to commit
- Then write `git commit -m "<ISSUE_NUMBER> LAB-<X>: <Business logic done it commit>"` *(you should change any `<...>` accordingly to your issue)*
- Then write `git push` and a prompt for a **pull request** should appear. If it doesn't, just go to the [repository site](https://git.pg.edu.pl/p1304534/single_sign_on) and crate it manually through gitlab UI.

### FAQ

Q: I created a pull request that:
- have not passed CI/CD
- have bugs in business logic
- still need changes *(on old commit)*

A: For purposes when the business logic is not changed but fixed:
- `git commit --amend --no-edit` if the previous commit message is correct.
- `git commit --amend -m "new message"` to overwrite previous commit message.
Then you should `git push --force`. The flag is required as you are trying to *change history*.

Q: I have conflicts in my pull request, what do I do?

A: Don't panic! Follow those steps :) :
- Go on your machine to the **destination** branch *(probably always `master`)* using `git checkout <branch_name>`
- Get all updates using `git pull`
- Go to the branch with conflicts using `git checkout <conflicting_branch_name>`
- Type `git rebase -i master`. *(It might open some messages, quit on them, we don't want to change commits messages)*
- `*` Resolve conflicts that both your changes and the incoming commit's changes are both present
- When there will be no conflicts left *(in VS Code they are indicated by red exclamation marks)* you should add changed files with command: `git add <path_to_file_or_directory>` 
- Then type `git rebase --continue`. If there are no conflicts left *(message in the console should indicate that)* you can proceed further. If there are next commits incoming, go to the step marked with `*`.
- You are practically done! Now `git push --force` will push the resolved changes onto your branch

