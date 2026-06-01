# Homebrew requirements
eval "$(/opt/homebrew/bin/brew shellenv)"
eval export HOMEBREW_PREFIX="/opt/homebrew"; export HOMEBREW_CELLAR="/opt/homebrew/Cellar"; export HOMEBREW_REPOSITORY="/opt/homebrew"; export PATH="/opt/homebrew/bin:/opt/homebrew/sbin${PATH+:$PATH}"; export MANPATH="/opt/homebrew/share/man${MANPATH+:$MANPATH}:"; export INFOPATH="/opt/homebrew/share/info:${INFOPATH:-}";

# MongoDB (installed via Homebrew)
# This should have an alias pointing to current version (8.2)
export PATH="/opt/homebrew/opt/mongodb-community/bin:$PATH"

# jenv (installed via Homebrew)
export PATH="$HOME/.jenv/bin:$PATH"
eval "$(jenv init -)"

# nvm (installed via Homebrew)
export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"  # This loads nvm
[ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"  # This loads nvm bash_completion

# Install (if needded) and run `nvm use` when a .nvmrc file is found
autoload -U add-zsh-hook

load-nvmrc() {
  local nvmrc_path
  nvmrc_path="$(nvm_find_nvmrc)"

  if [ -n "$nvmrc_path" ]; then
    local nvmrc_node_version
    nvmrc_node_version=$(nvm version "$(cat "${nvmrc_path}")")

    if [ "$nvmrc_node_version" = "N/A" ]; then
      nvm install
    elif [ "$nvmrc_node_version" != "$(nvm version)" ]; then
      nvm use
    fi
  elif [ -n "$(PWD=$OLDPWD nvm_find_nvmrc)" ] && [ "$(nvm version)" != "$(nvm version default)" ]; then
    echo "Reverting to nvm default version"
    nvm use default
  fi
}

add-zsh-hook chpwd load-nvmrc
load-nvmrc

# ========== Aliases (so lazy) ==========
alias gs='git status'

# macOS updates always undo this and it's infuriating
alias fixweb='chmod +a "_www allow execute" ~'

# I use Codium, not VS Code
alias code=codium

# Quick linting
alias tidy='npx eslint --fix'

# Handy for node.js app dev
alias TODO='grep -rnw "TODO" --exclude-dir=node_modules'
alias FIXME='grep -rnw "FIXME" --exclude-dir=node_modules'
alias CONSOLE='\grep -rn "console." --include="*.js" --exclude-dir=node_modules'
